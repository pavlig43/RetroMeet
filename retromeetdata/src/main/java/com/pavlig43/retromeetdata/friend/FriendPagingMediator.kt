package com.pavlig43.retromeetdata.friend

import android.util.Log
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import com.pavlig43.retromeetdata.common.FriendStatus
import com.pavlig43.retromeetdata.friend.api.FriendApi
import com.pavlig43.retromeetdata.searchUser.model.FriendPreviewResponse
import com.pavlig43.retromeetdata.searchUser.model.SearchUsersResponse
import com.pavlig43.retromeetdata.utils.database.RetromeetDataBase
import com.pavlig43.retromeetdata.utils.database.UsersRemoteKey
import retrofit2.HttpException
import retrofit2.Response

@Suppress("TooGenericExceptionCaught", "ReturnCount")
@OptIn(ExperimentalPagingApi::class)
class FriendPagingMediator(
    private val friendApi: FriendApi,
    private val database: RetromeetDataBase,
    private val userId: Int,
    private val friendStatus: FriendStatus,
) : RemoteMediator<Int, FriendPreviewResponse>() {

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, FriendPreviewResponse>
    ): MediatorResult {
        val result = try {
            val page = when (loadType) {
                LoadType.REFRESH -> {
                    database.searchDao.clearAll()
                    database.usersRemoteKeysDao.clearKeys(userId)
                    0
                }

                LoadType.PREPEND -> return MediatorResult.Success(endOfPaginationReached = true)
                LoadType.APPEND -> {
                    val remoteKey = database.usersRemoteKeysDao.getKey(userId)
                    remoteKey?.nextKey
                        ?: return MediatorResult.Success(endOfPaginationReached = true)
                }
            }

            val usersResponse: Response<SearchUsersResponse> = friendApi.getFriends(
                page = page,
                pageSize = state.config.pageSize,
                userId = userId,
                friendStatus = friendStatus,
            )
            val mediator: MediatorResult = if (usersResponse.isSuccessful) {
                val users =
                    usersResponse.body()?.users.orEmpty().map { it.copy(userId = userId) }

                val endOfPaginationReached = users.isEmpty()
                database.withTransaction {
                    database.searchDao.upsertUsers(users.map { it.copy(userId = userId) })
                    val nextKey = if (endOfPaginationReached) null else page + 1
                    database.usersRemoteKeysDao.insertOrReplace(
                        UsersRemoteKey(
                            userId,
                            page,
                            nextKey,
                            null
                        )
                    )
                }

                MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
            } else {
                MediatorResult.Error(Throwable(usersResponse.errorBody().toString()))
            }
            mediator
        } catch (e: HttpException) {
            MediatorResult.Error(e)
        } catch (e: Exception) {
            Log.d("FriendMediator", "Exception $e")
            MediatorResult.Error(e)
        }

        return result
    }
}
