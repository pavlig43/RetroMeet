package com.pavlig43.retromeetdata.friend

import android.util.Log
import com.pavlig43.retromeetdata.DateStoreSettings
import com.pavlig43.retromeetdata.friend.api.FriendRequestSocket
import jakarta.inject.Inject
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import java.net.URI

class ObserveFriendRequestRepository @Inject constructor(
    private val wsUrl: String,
    private val dataStore: DateStoreSettings

) {

    private var socket: FriendRequestSocket? = null

    @OptIn(ExperimentalCoroutinesApi::class)
    fun observeFriendRequest(): Flow<Int> {
        return dataStore.userId.flatMapLatest { id ->
            closeOldAndCreateNewWebSocket(id)
            socket?.count ?: flowOf(0)
        }
    }
    private fun closeOldAndCreateNewWebSocket(userId: Int) {
        socket?.close()
        val uri = URI("$wsUrl/friend-request/$userId")
        Log.d("URi Socket", uri.toString())
        socket = FriendRequestSocket(uri)
    }
}
