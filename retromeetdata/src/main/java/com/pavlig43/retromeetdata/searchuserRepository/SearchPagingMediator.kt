package com.pavlig43.retromeetdata.searchuserRepository

import android.util.Log
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import com.pavlig43.retromeetdata.searchuserRepository.api.SearchUserApi
import com.pavlig43.retromeetdata.searchuserRepository.model.FriendPreviewResponse
import com.pavlig43.retromeetdata.searchuserRepository.model.SearchUserFilterRequest
import com.pavlig43.retromeetdata.searchuserRepository.model.SearchUsersResponse
import com.pavlig43.retromeetdata.utils.database.RetromeetDataBase
import retrofit2.HttpException
import retrofit2.Response


@OptIn(ExperimentalPagingApi::class)
class SearchPagingMediator(
    private val searchUserApi: SearchUserApi,
    private val database: RetromeetDataBase,
    private val userId: Int?,
    private val filterRequest: SearchUserFilterRequest
) : RemoteMediator<Int, FriendPreviewResponse>() {
    override suspend fun initialize(): InitializeAction {
        return InitializeAction.LAUNCH_INITIAL_REFRESH
    }

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, FriendPreviewResponse>
    ): MediatorResult {
        val result = try {
            val lastId = when (loadType) {
                LoadType.REFRESH -> {
                    database.searchDao.clearAll()
                    Log.d("SearchPagingMediator REFRESH", "LoadType $loadType")

                    Log.d("SearchPagingMediator REFRESH", " pages ${state.pages}")
                    Int.MAX_VALUE

                }

                LoadType.PREPEND -> return MediatorResult.Success(endOfPaginationReached = true)
                LoadType.APPEND -> {
                    val lastId = state.lastItemOrNull()?.friendId
                    Log.d(
                        "SearchPagingMediator APPEND",
                        " lastItemId $lastId"
                    )
                    Log.d("SearchPagingMediator APPEND", " pages ${state.pages}")
                    lastId?.minus(1) ?: Int.MAX_VALUE

                }
            }


            val usersResponse: Response<SearchUsersResponse> = searchUserApi.searchUsers(
                lastId = lastId,
                pageSize = state.config.pageSize,
                userId = userId,
                searchUserFilterRequest = filterRequest,
            )
            val mediator: MediatorResult = if (usersResponse.isSuccessful) {
                val users =
                    usersResponse.body()?.users.orEmpty().map { it.copy(userId = userId ?: 0) }
                Log.d("SearchPagingMediator Response", " ${users.map { it.friendId }}")

                val endOfPaginationReached = users.size < state.config.pageSize
                database.withTransaction {
                    if (loadType == LoadType.REFRESH) {
                        database.searchDao.clearAll()
                    }

                    Log.d("users",users.toString())
                    database.searchDao.upsertUsers(users)
                }


                MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
            } else {
                MediatorResult.Error(Throwable(usersResponse.errorBody().toString()))
            }
            mediator
        } catch (e: HttpException) {
            MediatorResult.Error(e)
        } catch (e: Exception) {
            Log.d("SearchPagingMediator", "Exception $e")
            MediatorResult.Error(e)

        }

        return result
    }

}



