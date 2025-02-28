package com.pavlig43.retromeetdata.friendRepository

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.pavlig43.retromeetdata.common.FriendStatus
import com.pavlig43.retromeetdata.searchuserRepository.model.FriendPreviewResponse
import com.pavlig43.retromeetdata.utils.database.RetromeetDataBase

class FriendPagingSource(
    private val userId:Int,
    private val friendStatus: FriendStatus,
    private val database: RetromeetDataBase,
): PagingSource<Long, FriendPreviewResponse>() {
    override fun getRefreshKey(state: PagingState<Long, FriendPreviewResponse>): Long? {
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.friendStatus?.updatedAt
        }
    }
    private val TAG = "FriendPagingSource"
    init {
        Log.d(TAG,"init")
    }

    override suspend fun load(params: LoadParams<Long>): LoadResult<Long, FriendPreviewResponse> {

        return try {
            val updatedAt = params.key ?: Long.MAX_VALUE
            Log.d(TAG,"load $updatedAt")
            val users = database.friendDao.getFriends(
                updateAt = updatedAt,
                pageSize = params.loadSize,
                userId = userId,
                friendStatus = friendStatus
            )
            Log.d(TAG,"users $users")
            val nextKey = users.mapNotNull { it.friendStatus.updatedAt }.minByOrNull { it }

            Log.d(TAG,"nextKey $nextKey")



            LoadResult.Page(
                data = users,
                nextKey = nextKey?.minus(1),
                prevKey = null
            )
        }
        catch (e:Exception){
            LoadResult.Error(e)
        }

    }
}

