package com.pavlig43.retromeetdata.onlineRepository.api

import android.util.Log
import org.java_websocket.client.WebSocketClient
import org.java_websocket.handshake.ServerHandshake
import java.lang.Exception
import java.net.URI

class OnlineWebSocket(uri: URI):WebSocketClient(uri) {
    init {
        connect()

    }
    override fun onOpen(handshakedata: ServerHandshake?) {
        Log.d(ONLINE_WEBSOCKET,"connection Open")
//        send("Hello $uri")
    }

    override fun onMessage(message: String?) {
        Log.d(ONLINE_WEBSOCKET,"onMessage: $message")
    }

    override fun onClose(code: Int, reason: String?, remote: Boolean) {
        Log.d(ONLINE_WEBSOCKET,"Connection closed: Code $code, Reason $reason")
    }

    override fun onError(ex: Exception?) {
        Log.d(ONLINE_WEBSOCKET,"error: ${ex?.message}")
    }
    companion object {
        private const val ONLINE_WEBSOCKET = "OnlineWebSocket"
    }
}
