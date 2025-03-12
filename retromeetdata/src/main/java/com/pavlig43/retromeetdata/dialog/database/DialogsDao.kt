package com.pavlig43.retromeetdata.dialog.database

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.pavlig43.retromeetdata.dialog.model.DialogResponse

@Dao
interface DialogsDao {

    @Query(
        """
        SELECT * FROM dialogs
        WHERE (dialog_user_id =:userId) 
        ORDER BY dialog_updated_at DESC
        """
    )
    fun pagingDialogs(
        userId: Int,
    ): PagingSource<Int, DialogResponse>

    @Query("SELECT * FROM dialogs WHERE dialog_server_id =:serverId ")
    suspend fun getDialogsByServerId(serverId: Long): DialogResponse?

    @Update
    suspend fun updateDialog(dialog: DialogResponse)

    @Insert
    suspend fun insertDialog(dialog: DialogResponse): Long

    @Transaction
    suspend fun upsertDialogs(dialogs: List<DialogResponse>) {
        for (dialog in dialogs) {
            val existingDialog = getDialogsByServerId(dialog.info.serverId ?: -1)
            if (existingDialog != null) {
                updateDialog(dialog)
            } else {
                insertDialog(dialog)
            }
        }
    }
}
