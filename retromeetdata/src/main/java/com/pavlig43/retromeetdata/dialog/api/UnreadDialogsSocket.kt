package com.pavlig43.retromeetdata.dialog.api

import android.util.Log
import com.pavlig43.retromeetdata.dialog.model.UnreadDialogsResponse
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.update
import kotlinx.serialization.json.Json
import org.java_websocket.client.WebSocketClient
import org.java_websocket.handshake.ServerHandshake
import java.lang.Exception
import java.net.URI

class UnreadDialogsSocket(uri: URI) : WebSocketClient(uri) {

    init {
        Log.d(TAG, "$uri")
        connect()
    }
    private val _unreadDialogs = MutableStateFlow(UnreadDialogsResponse())
    val unreadDialogs = _unreadDialogs.asSharedFlow()
    override fun onOpen(handshakedata: ServerHandshake?) {
        Log.d(TAG, "connection Open $uri")
    }

    override fun onMessage(message: String?) {
        Log.d(TAG, "onMessage: $message")
        message?.let {
            val unread = Json.decodeFromString<UnreadDialogsResponse>(message)
            _unreadDialogs.update { unread }
        }
    }

    override fun onClose(code: Int, reason: String?, remote: Boolean) {
        Log.d(TAG, "Connection closed: Code $code, Reason $reason")
    }

    override fun onError(ex: Exception?) {
        Log.d(TAG, "error: ${ex?.message}")
    }
    companion object {
        private const val TAG = "UNREAD DIALOG WEBSOCKET"
    }
}
