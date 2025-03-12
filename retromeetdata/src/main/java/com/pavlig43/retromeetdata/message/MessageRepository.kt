package com.pavlig43.retromeetdata.message

import android.util.Log
import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.pavlig43.retromeetcommon.Logger
import com.pavlig43.retromeetdata.DateStoreSettings
import com.pavlig43.retromeetdata.message.api.MessageApi
import com.pavlig43.retromeetdata.message.model.AttachmentRequest
import com.pavlig43.retromeetdata.message.model.AttachmentResponse
import com.pavlig43.retromeetdata.message.model.MessageRequest
import com.pavlig43.retromeetdata.message.model.MessageResponse
import com.pavlig43.retromeetdata.message.model.MessageStatus
import com.pavlig43.retromeetdata.utils.database.RetromeetDataBase
import com.pavlig43.retromeetdata.utils.requestResult.RequestResult
import com.pavlig43.retromeetdata.utils.requestResult.toRequestResult
import jakarta.inject.Inject
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.datetime.Clock

class MessageRepository @Inject constructor(
    private val dataStore: DateStoreSettings,
    private val database: RetromeetDataBase,
    private val logger: Logger,
    private val messageApi: MessageApi
) {

    @OptIn(ExperimentalCoroutinesApi::class, ExperimentalPagingApi::class)
    fun getMessages(friendId: Int): Flow<PagingData<MessageResponse>> {
        return dataStore.userId.map { userId ->
            Pager(
                config = PagingConfig(PAGE_SIZE),
                pagingSourceFactory = { database.messageDao.pagingMessages(userId, friendId) },
                remoteMediator = MessagesPagingMediator(
                    messageApi = messageApi,
                    database = database,
                    userId = userId,
                    friendId = friendId
                )
            ).flow
        }.flatMapLatest { it }
    }

    suspend fun sendMessage(messageRequest: MessageRequest): RequestResult<Unit> {
        val userId = dataStore.userId.first()
        val id = database.messageDao.insertMessage(messageRequest.toMessageResponse(userId))
        Log.d(TAG, "localId $id")
        val response = messageApi.sendMessage(messageRequest.copy(sender = userId))

        if (response.isSuccessful) {
            database.messageDao.updateMessageStatus(id, MessageStatus.LOAD)
        } else {
            database.messageDao.updateMessageStatus(id, MessageStatus.ERROR)
        }
        return response.toRequestResult(TAG, logger)
    }

    /**
     * для первичного сохранения в базу данных,потом обновится если неудачно на сервер пойдет
     */
    private fun MessageRequest.toMessageResponse(userId: Int): MessageResponse {
        return MessageResponse(
            serverId = null,
            sender = userId,
            recipient = recipient,
            replyMessageId = replyMessageId,
            text = text,
            time = Clock.System.now().toEpochMilliseconds(),
            status = MessageStatus.IN_PROCESS,
            attachment = attachment?.toAttachmentResponse(),
        )
    }
    private fun AttachmentRequest.toAttachmentResponse(): AttachmentResponse {
        return AttachmentResponse(
            id = 1,
            senderPath = senderPath,
            recipientPath = null,
            type = type
        )
    }

    companion object {
        private const val TAG = "Message Repository"
        private const val PAGE_SIZE = 3
    }
}
