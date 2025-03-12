package com.pavlig43.retromeetdata.online

import android.util.Log
import com.pavlig43.retromeetdata.DateStoreSettings
import com.pavlig43.retromeetdata.online.api.OnlineWebSocket
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.net.URI

class OnlineRepository(
    private val wsUrl: String,
    private val dateStore: DateStoreSettings

) {
    private var socket: OnlineWebSocket? = null

    fun closeOldAndCreateNewWebSocket(): Flow<Unit> {
        return dateStore.userId.map { userId ->
            socket?.close()
            val uri = URI("$wsUrl/online/$userId")
            Log.d("URi Socket", uri.toString())
            socket = OnlineWebSocket(uri)
        }
    }
}
