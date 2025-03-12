package com.pavlig43.retromeetdata.utils.database

import androidx.room.ColumnInfo
import androidx.room.Dao
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.PrimaryKey
import androidx.room.Query
import com.pavlig43.retromeetdata.common.USER_ID

@Dao
interface UsersRemoteKeysDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrReplace(usersRemoteKey: UsersRemoteKey)

    @Query("SELECT * FROM users_remote_keys WHERE user_id = :userId")
    suspend fun getKey(userId: Int): UsersRemoteKey?

    @Query("DELETE FROM users_remote_keys WHERE user_id = :userId")
    suspend fun clearKeys(userId: Int)
}

@Entity("users_remote_keys")
data class UsersRemoteKey(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(USER_ID)
    val userId: Int,

    @ColumnInfo("current_key")
    val currentKey: Int?,

    @ColumnInfo("next_key")
    val nextKey: Int?,

    @ColumnInfo("prev_key")
    val prevKey: Int?,
)
