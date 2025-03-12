package com.pavlig43.retromeetdata.dialog

import android.util.Log
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import com.pavlig43.retromeetdata.dialog.api.DialogApi
import com.pavlig43.retromeetdata.dialog.database.DialogsRemoteKey
import com.pavlig43.retromeetdata.dialog.model.DialogResponse
import com.pavlig43.retromeetdata.utils.database.RetromeetDataBase
import retrofit2.HttpException

@Suppress("TooGenericExceptionCaught", "ReturnCount")
@OptIn(ExperimentalPagingApi::class)
class DialogsPagingMediator(
    private val dialogApi: DialogApi,
    private val database: RetromeetDataBase,
    private val userId: Int,
) : RemoteMediator<Int, DialogResponse>() {
    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, DialogResponse>
    ): MediatorResult {
        val result = try {
            val page = when (loadType) {
                LoadType.REFRESH -> {
                    database.dialogsKeysDao.clearKeys(userId)
                    0
                }

                LoadType.PREPEND -> return MediatorResult.Success(endOfPaginationReached = true)
                LoadType.APPEND -> {
                    val remoteKey = database.messagesRemoteKeysDao.getKey(userId)
                    remoteKey?.nextKey ?: return MediatorResult.Success(endOfPaginationReached = true)
                }
            }
            val dialogsResponse = dialogApi.getDialogs(
                page = page,
                pageSize = state.config.pageSize,
                userId = userId
            )
            val mediator = if (dialogsResponse.isSuccessful) {
                val dialogs = dialogsResponse.body()?.dialogs ?: listOf()
                val endOfPaginationReached = dialogs.isEmpty()
                database.withTransaction {
                    database.dialogsDao.upsertDialogs(dialogs)
                    val nextKey = if (endOfPaginationReached) null else page + 1
                    database.dialogsKeysDao.insertOrReplace(DialogsRemoteKey(userId, page, nextKey, null))
                }
                MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
            } else {
                MediatorResult.Error(Throwable(dialogsResponse.errorBody().toString()))
            }
            mediator
        } catch (e: HttpException) {
            MediatorResult.Error(e)
        } catch (e: Exception) {
            Log.d("DialogMediator", "Exception $e")
            MediatorResult.Error(e)
        }

        return result
    }
}
