package com.pavlig43.retromeetdata.message.model

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MessagesResponse(
    val messages: List<MessageResponse>
)

@Entity(
    "message",
)
@Serializable
data class MessageResponse(

    @ColumnInfo("server_id")
    @SerialName("server_id")
    val serverId: Long?,

    @ColumnInfo("sender")
    @SerialName("sender")
    val sender: Int,

    @ColumnInfo("recipient")
    @SerialName("recipient")
    val recipient: Int,

    @ColumnInfo("reply_message_id")
    @SerialName("reply_message_id")
    val replyMessageId: Long?,

    @ColumnInfo("text")
    @SerialName("text")
    val text: String,

    @ColumnInfo("time")
    @SerialName("time")
    val time: Long,

    @ColumnInfo("status")
    @SerialName("status")
    val status: MessageStatus,

    @Embedded("attachment_")
    @SerialName("attachment")
    val attachment: AttachmentResponse? = null,

    @PrimaryKey(autoGenerate = true)
    @SerialName("local_id")
    val localId: Long = 0,
)

@Serializable
enum class MessageStatus {
    READ,
    LOAD,
    ERROR,
    IN_PROCESS
}

@Serializable
data class AttachmentResponse(

    @SerialName("id")
    val id: Long,

    @SerialName("sender_path")
    val senderPath: String?,

    @SerialName("recipient_path")
    val recipientPath: String?,

    @SerialName("type")
    val type: AttachmentType
)
