package com.pavlig43.features.message

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.pavlig43.features.message.model.MessageStatusUi
import com.pavlig43.features.message.model.MessageResponseUi
import com.pavlig43.features.message.route.MessagesRoute
import com.pavlig43.features.utils.convertToDateTime
import com.pavlig43.retromeetdata.message.MessageRepository
import com.pavlig43.retromeetdata.message.model.AttachmentRequest
import com.pavlig43.retromeetdata.message.model.AttachmentType
import com.pavlig43.retromeetdata.message.model.MessageRequest
import com.pavlig43.retromeetdata.message.model.MessageResponse
import com.pavlig43.retromeetdata.message.model.MessageStatus
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class MessagesViewModel @Inject constructor(
    private val messageRepository: MessageRepository,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val friendId = checkNotNull(savedStateHandle.toRoute<MessagesRoute>().userId)

    private val _messageRequest = MutableStateFlow(MessageRequestUi())
    val messageRequest = _messageRequest.asStateFlow()

    fun onValueChangeText(text: String) {
        _messageRequest.update { it.copy(text = text) }
    }


    val messages = messageRepository.getMessages(friendId)
        .map { pagingData -> pagingData.map { it.toMessageUi(friendId) } }
        .cachedIn(viewModelScope)
        .stateIn(viewModelScope, SharingStarted.Lazily, PagingData.empty())

    fun retrySendMessage(messageId: Long) {

    }

    fun sendMessage() {
        viewModelScope.launch {
            val text = _messageRequest.value.text
            val attachment = _messageRequest.value.attachment
            if (text.isNotBlank() || attachment != null) {
                messageRepository.sendMessage(_messageRequest.value.toMessageRequest(friendId))
            }
            _messageRequest.update { MessageRequestUi() }
        }

    }
}

private fun MessageResponse.toMessageUi(friendId: Int): MessageResponseUi {
    return MessageResponseUi(
        localId = localId,
        serverId = serverId,
        sender = sender,
        recipient = this.recipient,
        userIsSender = sender != friendId,
        replyMessageId = replyMessageId,
        text = text,
        time = time.convertToDateTime(),
        status = status.toMessageStatusUI(),
    )
}

private fun MessageStatus.toMessageStatusUI(): MessageStatusUi {
    return MessageStatusUi.valueOf(this.name)
}

data class MessageRequestUi(
    val text: String = "",
    val replyMessageId: Long? = null,
    val attachment: AttachmentRequestUi? = null
)

data class AttachmentRequestUi(
    val senderPath: String,
    val type: AttachmentType
)

private fun AttachmentRequestUi.toAttachmentRequest(): AttachmentRequest {
    return AttachmentRequest(
        senderPath = senderPath,
        type = type
    )
}

private fun MessageRequestUi.toMessageRequest(recipient: Int): MessageRequest {
    return MessageRequest(

        recipient = recipient,
        replyMessageId = replyMessageId,
        text = text,
        attachment = attachment?.toAttachmentRequest()
    )
}
