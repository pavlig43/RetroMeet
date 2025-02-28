package com.pavlig43.retromeetdata.friendRepository

import android.util.Log
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import com.pavlig43.retromeetdata.common.FriendStatus
import com.pavlig43.retromeetdata.friendRepository.api.FriendApi
import com.pavlig43.retromeetdata.searchuserRepository.model.FriendPreviewResponse
import com.pavlig43.retromeetdata.searchuserRepository.model.SearchUsersResponse
import com.pavlig43.retromeetdata.utils.database.RetromeetDataBase
import retrofit2.HttpException
import retrofit2.Response


@OptIn(ExperimentalPagingApi::class)
class FriendPagingMediator(
    private val friendApi: FriendApi,
    private val database: RetromeetDataBase,
    private val userId: Int?,
    private val friendStatus: FriendStatus,
) : RemoteMediator<Long, FriendPreviewResponse>() {



    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Long, FriendPreviewResponse>
    ): MediatorResult {
        val result = try {
            val updateAt = when (loadType) {
                LoadType.REFRESH -> {
                    database.searchDao.clearAll()
                    Log.d("FriendMediator REFRESH", "LoadType $loadType")

                    Log.d("FriendMediator REFRESH", " pages ${state.pages}")
                    Long.MAX_VALUE

                }

                LoadType.PREPEND -> return MediatorResult.Success(endOfPaginationReached = true)
                LoadType.APPEND -> {
                    val updateAt = state.lastItemOrNull()?.friendStatus?.updatedAt
                    Log.d(
                        "FriendMediator APPEND",
                        " lastItemId $updateAt"
                    )
                    Log.d("FriendMediator APPEND", " pages ${state.pages}")
                    updateAt?.minus(1)?:Long.MAX_VALUE

                }
            }


            val usersResponse: Response<SearchUsersResponse> = friendApi.getFriends(
                updateAt = updateAt,
                pageSize = state.config.pageSize,
                userId = userId,
                friendStatus = friendStatus,
            )
            val mediator: MediatorResult = if (usersResponse.isSuccessful) {
                val users =
                    usersResponse.body()?.users.orEmpty().map { it.copy(userId = userId ?: 0) }
                Log.d("FriendMediator Response", " ${users.map { it.friendId }}")

                val endOfPaginationReached = users.size < state.config.pageSize
                database.withTransaction {
                    database.searchDao.upsertUsers(users.map { it.copy(userId = userId?:0) })
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
    }}
