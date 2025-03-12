package com.pavlig43.retromeetdata.message.database

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.pavlig43.retromeetdata.message.model.MessageResponse
import com.pavlig43.retromeetdata.message.model.MessageStatus

@Dao
interface MessageDao {

    @Query(
        """
        SELECT * FROM message
        WHERE ((sender = :userId AND recipient = :friendId) OR (sender = :friendId AND recipient = :userId))
        ORDER BY time DESC
        """
    )
    fun pagingMessages(
        userId: Int,
        friendId: Int,
    ): PagingSource<Int, MessageResponse>

    @Query("SELECT * FROM message WHERE server_id =:serverId ")
    suspend fun getMessageByServerId(serverId: Long): MessageResponse?

    @Update
    suspend fun updateMessage(message: MessageResponse)

    @Insert
    suspend fun insertMessage(message: MessageResponse): Long

    @Query("UPDATE message SET status=:status WHERE localId=:localId")
    suspend fun updateMessageStatus(localId: Long, status: MessageStatus)

    @Transaction
    suspend fun upsertMessages(messages: List<MessageResponse>) {
        for (message in messages) {
            val existingMessage = getMessageByServerId(message.serverId ?: -1)
            if (existingMessage != null) {
                updateMessage(message)
            } else {
                insertMessage(message)
            }
        }
    }
}
