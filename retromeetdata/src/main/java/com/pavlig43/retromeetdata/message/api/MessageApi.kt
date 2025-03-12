package com.pavlig43.retromeetdata.message.api

import com.pavlig43.retromeetdata.common.FRIEND_ID
import com.pavlig43.retromeetdata.common.PAGE
import com.pavlig43.retromeetdata.common.PAGE_SIZE
import com.pavlig43.retromeetdata.common.USER_ID
import com.pavlig43.retromeetdata.message.model.MessageRequest
import com.pavlig43.retromeetdata.message.model.MessagesResponse
import com.pavlig43.retromeetdata.utils.api.createRetrofitApi
import okhttp3.OkHttpClient
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

private const val ROUTE = "messages"
interface MessageApi {

    @POST(ROUTE)
    suspend fun sendMessage(
        @Body message: MessageRequest
    ): Response<Unit>

    @GET(ROUTE)
    suspend fun getMessagesByFriendId(
        @Query(PAGE) page: Int,
        @Query(PAGE_SIZE) pageSize: Int,
        @Query(USER_ID) userId: Int,
        @Query(FRIEND_ID) friendId: Int
    ): Response<MessagesResponse>
}

fun MessageApi(
    baseUrl: String,
    client: OkHttpClient? = null,

) = createRetrofitApi<MessageApi>(
    baseUrl,
    client,

)
