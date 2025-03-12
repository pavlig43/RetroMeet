package com.pavlig43.retromeetdata.message

import android.util.Log
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import com.pavlig43.retromeetdata.message.api.MessageApi
import com.pavlig43.retromeetdata.message.database.MessagesRemoteKey
import com.pavlig43.retromeetdata.message.model.MessageResponse
import com.pavlig43.retromeetdata.message.model.MessageStatus
import com.pavlig43.retromeetdata.utils.database.RetromeetDataBase
import retrofit2.HttpException

@Suppress("TooGenericExceptionCaught", "ReturnCount")
@OptIn(ExperimentalPagingApi::class)
class MessagesPagingMediator(
    private val messageApi: MessageApi,
    private val database: RetromeetDataBase,
    private val userId: Int,
    private val friendId: Int,
) : RemoteMediator<Int, MessageResponse>() {

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, MessageResponse>
    ): MediatorResult {
        when (loadType) {
            LoadType.REFRESH -> database.messagesRemoteKeysDao.insertOrReplace(
                MessagesRemoteKey(
                    userId,
                    0,
                    1,
                    null
                )

            )


            LoadType.PREPEND -> return MediatorResult.Success(endOfPaginationReached = true)
            LoadType.APPEND -> {
                val remoteKey = database.messagesRemoteKeysDao.getKey(userId)
                val newKey = MessagesRemoteKey(
                    userId,
                    remoteKey?.currentKey?.plus(1),
                    remoteKey?.nextKey?.plus(1),
                    null
                )
                database.messagesRemoteKeysDao.insertOrReplace(newKey)
//                remoteKey?.nextKey
//                    ?: return MediatorResult.Success(endOfPaginationReached = true)
            }
        }
        val result = getMediatorResult(state.config.pageSize)
        return result

    }

//    override suspend fun load(
//        loadType: LoadType,
//        state: PagingState<Int, MessageResponse>
//    ): MediatorResult {
//        val result = try {
//            val page = when (loadType) {
//                LoadType.REFRESH -> {
//                    database.messagesRemoteKeysDao.clearKeys(userId)
//                    0
//                }
//
//                LoadType.PREPEND -> return MediatorResult.Success(endOfPaginationReached = true)
//                LoadType.APPEND -> {
//                    val remoteKey = database.messagesRemoteKeysDao.getKey(userId)
//                    remoteKey?.nextKey
//                        ?: return MediatorResult.Success(endOfPaginationReached = true)
//                }
//            }
//
//            val messagesResponse: Response<MessagesResponse> = messageApi.getMessagesByFriendId(
//                page = page,
//                pageSize = state.config.pageSize,
//                userId = userId,
//                friendId = friendId,
//            )
//            val mediator: MediatorResult = if (messagesResponse.isSuccessful) {
//                val messages = messagesResponse.body()?.messages ?: listOf()
//
//                val endOfPaginationReached = messages.isEmpty()
//                database.withTransaction {
//                    database.messageDao.upsertMessages(messages)
//                    val nextKey = if (endOfPaginationReached) null else page + 1
//                    database.messagesRemoteKeysDao.insertOrReplace(
//                        MessagesRemoteKey(
//                            userId,
//                            page,
//                            nextKey,
//                            null
//                        )
//                    )
//                }
//
//                MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
//            } else {
//                MediatorResult.Error(Throwable(messagesResponse.errorBody().toString()))
//            }
//            mediator
//        } catch (e: HttpException) {
//            MediatorResult.Error(e)
//        } catch (e: Exception) {
//            Log.d("MessageMediator", "Exception $e")
//            MediatorResult.Error(e)
//        }
//
//        return result
//    }

    private suspend fun getMediatorResult(
        pageSize: Int
    ): MediatorResult {
        val remoteKey = database.messagesRemoteKeysDao.getKey(userId)
        val page = remoteKey?.nextKey ?: 0
        val response = messageApi.getMessagesByFriendId(
            page,
            pageSize = pageSize,
            userId = userId,
            friendId = friendId
        )

        val mediator = try {

            if (response.isSuccessful) {
                val messages = response.body()?.messages ?: listOf()
                val endOfPaginationReached = messages.isEmpty()
                database.withTransaction {
                    database.messageDao.upsertMessages(messages)
                    val nextKey = if (endOfPaginationReached) null else page + 1
                    database.messagesRemoteKeysDao.insertOrReplace(
                        MessagesRemoteKey(
                            userId,
                            page,
                            nextKey,
                            null
                        )
                    )
                }
                if (messages.lastOrNull()?.status == MessageStatus.LOAD && !endOfPaginationReached) {
                    Log.d("MEssageMEdiator", messages.lastOrNull().toString())
                    getMediatorResult(pageSize)
                } else {
                    MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
                }

            } else {
                MediatorResult.Error(Throwable(response.errorBody().toString()))
            }
        } catch (e: HttpException) {
            MediatorResult.Error(e)
        } catch (e: Exception) {
            Log.d("MessageMediator", "Exception $e")
            MediatorResult.Error(e)
        }
        return mediator
    }

}
