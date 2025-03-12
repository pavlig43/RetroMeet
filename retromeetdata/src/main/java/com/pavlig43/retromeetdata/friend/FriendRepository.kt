package com.pavlig43.retromeetdata.friend

import android.util.Log
import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.pavlig43.retromeetcommon.Logger
import com.pavlig43.retromeetdata.DateStoreSettings
import com.pavlig43.retromeetdata.common.FriendStatus
import com.pavlig43.retromeetdata.friend.api.FriendApi
import com.pavlig43.retromeetdata.friend.api.FriendRequestApi
import com.pavlig43.retromeetdata.searchUser.model.FriendPreviewResponse
import com.pavlig43.retromeetdata.utils.database.RetromeetDataBase
import com.pavlig43.retromeetdata.utils.requestResult.RequestResult
import com.pavlig43.retromeetdata.utils.requestResult.toRequestResult
import jakarta.inject.Inject
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map

class FriendRepository @Inject constructor(
    private val friendApi: FriendApi,
    private val friendRequestApi: FriendRequestApi,
    private val dateStore: DateStoreSettings,
    private val database: RetromeetDataBase,
    private val logger: Logger
) {

    suspend fun changeFriendStatus(friendId: Int, friendStatus: FriendStatus): RequestResult<Unit> {
        val userId = dateStore.userId.first()

        val result = when (friendStatus) {
            FriendStatus.FRIEND -> friendApi.deleteFriend(userId, friendId)
            FriendStatus.REQUEST_PLUS -> friendRequestApi.cancelFriendRequest(friendId, userId)
            FriendStatus.REQUEST_MINUS -> friendApi.acceptFriendRequest(userId, friendId)
            FriendStatus.NO_DATA -> friendRequestApi.sendRequest(userId, friendId)
        }.toRequestResult(TAG, logger)
        if (result is RequestResult.Success) {
            Log.d(TAG, "changeFriendStatus: $friendStatus")
        }
        return result
    }
    suspend fun canselRequest(friendId: Int): RequestResult<Unit> {
        val userId = dateStore.userId.first()
        val result = friendRequestApi.cancelFriendRequest(userId, friendId)
            .toRequestResult(TAG, logger)
        return result
    }

    @OptIn(ExperimentalPagingApi::class, ExperimentalCoroutinesApi::class)
    fun getFriends(friendStatus: FriendStatus): Flow<PagingData<FriendPreviewResponse>> {
        return dateStore.userId.map { userId ->
            Pager(
                config = PagingConfig(PAGE_SIZE),
                pagingSourceFactory = { database.friendDao.pagingFriend(userId, friendStatus) },
                remoteMediator = FriendPagingMediator(
                    friendApi = friendApi,
                    database = database,
                    userId = userId,
                    friendStatus = friendStatus
                )
            ).flow
        }.flatMapLatest { it }
    }

    companion object {
        private const val TAG = "Friend Repository"
        private const val PAGE_SIZE = 10
    }
}
