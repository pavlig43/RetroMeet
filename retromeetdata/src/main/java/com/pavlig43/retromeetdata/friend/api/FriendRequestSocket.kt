package com.pavlig43.retromeetdata.friend.api

import android.util.Log
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.update
import org.java_websocket.client.WebSocketClient
import org.java_websocket.handshake.ServerHandshake
import java.lang.Exception
import java.net.URI

class FriendRequestSocket(uri: URI) : WebSocketClient(uri) {
    init {
        connect()
    }
    private val _count = MutableStateFlow(0)
    val count = _count.asSharedFlow()

    override fun onOpen(handshakedata: ServerHandshake?) {
        Log.d(FRIEND_REQUEST_WEBSOCKET, "connection Open")
        send("Hello $uri")
    }

    override fun onMessage(message: String?) {
        Log.d(FRIEND_REQUEST_WEBSOCKET, "onMessage: $message")
        message?.toIntOrNull()?.let { count ->
            _count.update { count }
        }
    }

    override fun onClose(code: Int, reason: String?, remote: Boolean) {
        Log.d(FRIEND_REQUEST_WEBSOCKET, "Connection closed: Code $code, Reason $reason")
    }

    override fun onError(ex: Exception?) {
        Log.d(FRIEND_REQUEST_WEBSOCKET, "error: ${ex?.message}")
    }
    companion object {
        private const val FRIEND_REQUEST_WEBSOCKET = "FriendRequestSocket"
    }
}
