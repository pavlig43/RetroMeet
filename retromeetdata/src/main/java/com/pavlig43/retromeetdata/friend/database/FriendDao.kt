package com.pavlig43.retromeetdata.friend.database

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Query
import com.pavlig43.retromeetdata.common.FriendStatus
import com.pavlig43.retromeetdata.searchUser.model.FriendPreviewResponse

@Dao
interface FriendDao {

    @Query(
        "SELECT * FROM all_users " +
            "WHERE user_id = :userId AND friend_status = :friendStatus  ORDER BY  friend_updated_at DESC"
    )
    fun pagingFriend(userId: Int, friendStatus: FriendStatus): PagingSource<Int, FriendPreviewResponse>
}
