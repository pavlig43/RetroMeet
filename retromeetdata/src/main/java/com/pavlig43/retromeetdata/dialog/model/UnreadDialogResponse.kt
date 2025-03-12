package com.pavlig43.retromeetdata.dialog.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UnreadDialogsResponse(
    @SerialName("user_id")
    val userId: Int = 0,
    @SerialName("unread")
    val unreadDialogs: List<UnreadDialog> = listOf()
)

@Serializable
data class UnreadDialog(
    @SerialName("dialog_id")
    val dialogId: Long = 0,
    @SerialName("count")
    val count: Int = 0
)
