package com.pavlig43.retromeetdata.searchUser.database

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Upsert
import com.pavlig43.retromeetdata.common.FriendStatus
import com.pavlig43.retromeetdata.searchUser.model.FriendPreviewResponse
import com.pavlig43.retromeetdata.searchUser.model.SearchUserFilterRequest
import kotlinx.coroutines.flow.Flow

@Dao
interface SearchDao {

    @Query("SELECT * FROM search_filter LIMIT 1")
    fun getSearchFilter(): Flow<SearchUserFilterRequest>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveSearchFilter(searchFilter: SearchUserFilterRequest)

    @Query("SELECT * FROM all_users WHERE user_id = :userId ORDER BY friend_id DESC")
    fun getUsersWithFilter(userId: Int): PagingSource<Int, FriendPreviewResponse>

    @Upsert
    suspend fun upsertUsers(users: List<FriendPreviewResponse>)

    @Upsert
    suspend fun upsertUser(user: FriendPreviewResponse)

    @Query(
        """
        UPDATE all_users 
        SET friend_status = :status,friend_updated_at = :updatedAt
        WHERE user_id = :userId AND friend_id = :friendId
    """
    )
    suspend fun updateFriendStatus(
        userId: Int,
        friendId: Int,
        status: FriendStatus,
        updatedAt: Long?
    ): Int

    @Query("DELETE FROM all_users")
    suspend fun clearAll()
}
