package com.pavlig43.features.message.model

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.ErrorOutline
import androidx.compose.material.icons.filled.Sync
import androidx.compose.ui.graphics.vector.ImageVector
import com.pavlig43.features.R
import com.pavlig43.retromeetdata.message.model.AttachmentResponse

data class MessageResponseUi(
    val localId: Long,
    val serverId:Long?,
    val sender: Int,
    val recipient: Int,
    val userIsSender:Boolean,
    val replyMessageId: Long?,
    val text: String,
    val time: String,
    val status: MessageStatusUi,
    val attachment: AttachmentResponse? = null,

    )
enum class MessageStatusUi( val icon:ImageVector, @StringRes val contentDescription:Int,val isAnimate:Boolean = false){
    READ(Icons.Default.CheckCircle, R.string.message_read),
    LOAD(Icons.Default.Check,R.string.message_load),
    ERROR(Icons.Default.ErrorOutline,R.string.error),
    IN_PROCESS(Icons.Default.Sync,R.string.sending,true)
}



