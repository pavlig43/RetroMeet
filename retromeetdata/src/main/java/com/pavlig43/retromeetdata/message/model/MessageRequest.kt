package com.pavlig43.retromeetdata.message.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MessageRequest(
    @SerialName("sender")
    val sender: Int = 0,

    @SerialName("recipient")
    val recipient: Int,

    @SerialName("reply_message_id")
    val replyMessageId: Long? = null,

    @SerialName("text")
    val text: String,

    @SerialName("attachment")
    val attachment: AttachmentRequest?,

)

@Serializable
data class AttachmentRequest(

    @SerialName("sender_path")
    val senderPath: String,

    @SerialName("type")
    val type: AttachmentType
)
