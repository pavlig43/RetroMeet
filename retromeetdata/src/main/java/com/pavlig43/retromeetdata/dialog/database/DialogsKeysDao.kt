package com.pavlig43.retromeetdata.dialog.database

import androidx.room.ColumnInfo
import androidx.room.Dao
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.PrimaryKey
import androidx.room.Query
import com.pavlig43.retromeetdata.common.USER_ID

@Dao
interface DialogsKeysDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrReplace(messagesRemoteKey: DialogsRemoteKey)

    @Query("SELECT * FROM dialogs_remote_keys WHERE user_id = :userId")
    suspend fun getKey(userId: Int): DialogsRemoteKey?

    @Query("DELETE FROM dialogs_remote_keys WHERE user_id = :userId")
    suspend fun clearKeys(userId: Int)
}

@Entity("dialogs_remote_keys")
data class DialogsRemoteKey(
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
