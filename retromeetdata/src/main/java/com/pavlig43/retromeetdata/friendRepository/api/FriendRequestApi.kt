package com.pavlig43.retromeetdata.friendRepository.api

import com.pavlig43.retromeetdata.common.FRIEND_ID
import com.pavlig43.retromeetdata.common.USER_ID
import com.pavlig43.retromeetdata.utils.api.createRetrofitApi
import kotlinx.serialization.json.Json
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Response
import retrofit2.http.DELETE
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query


private const val ROUTE = "friend-request"

interface FriendRequestApi {
    @DELETE(ROUTE)
    suspend fun cancelFriendRequest(
        @Query(USER_ID) userId:Int,
        @Query(FRIEND_ID) friendId:Int,
    ): Response<Unit>


    @POST(ROUTE)
    suspend fun sendRequest(
        @Query(USER_ID) userId:Int,
        @Query(FRIEND_ID) friendId:Int,
    ): Response<Unit>
}




fun FriendRequestApi(
    baseUrl: String,
    client: OkHttpClient? = null,

    interceptor: Interceptor? = null,
    json: Json = Json,
) = createRetrofitApi<FriendRequestApi>(
    baseUrl,
    client,
    interceptor,
    json
)