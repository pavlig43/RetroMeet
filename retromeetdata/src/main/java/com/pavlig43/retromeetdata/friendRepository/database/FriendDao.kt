package com.pavlig43.retromeetdata.friendRepository.database

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import com.pavlig43.retromeetdata.common.FriendStatus
import com.pavlig43.retromeetdata.searchuserRepository.model.FriendPreviewResponse

@Dao
interface FriendDao {

    @Query("SELECT * FROM all_users " +
            "WHERE user_id = :userId " +
            "AND friend_updated_at<:updateAt " +
            "AND friend_status = :friendStatus " +
            "ORDER BY friend_updated_at DESC " +
            "LIMIT :pageSize")
    suspend fun getFriends(
        updateAt: Long,
        pageSize: Int,
        userId: Int,
        friendStatus: FriendStatus
    ): List<FriendPreviewResponse>


}