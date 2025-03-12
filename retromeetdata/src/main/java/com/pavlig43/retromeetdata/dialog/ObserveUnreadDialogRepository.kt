package com.pavlig43.retromeetdata.dialog

import android.util.Log
import com.pavlig43.retromeetdata.DateStoreSettings
import com.pavlig43.retromeetdata.dialog.api.UnreadDialogsSocket
import com.pavlig43.retromeetdata.dialog.model.UnreadDialogsResponse
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import java.net.URI

class ObserveUnreadDialogRepository(
    private val wsUrl: String,
    private val dataStore: DateStoreSettings
) {
    private var socket: UnreadDialogsSocket? = null

    @OptIn(ExperimentalCoroutinesApi::class)
    fun observeUnreadMessageWithSender(): Flow<UnreadDialogsResponse> {
        return dataStore.userId.flatMapLatest { userId ->
            closeOldAndCreateNewWebSocket(userId)
            socket?.unreadDialogs ?: flowOf(UnreadDialogsResponse())
        }
    }

    private fun closeOldAndCreateNewWebSocket(userId: Int) {
        socket?.close()
        val uri = URI("$wsUrl/unread-dialogs/$userId")
        Log.d("URi Socket", uri.toString())
        socket = UnreadDialogsSocket(uri)
    }
}
