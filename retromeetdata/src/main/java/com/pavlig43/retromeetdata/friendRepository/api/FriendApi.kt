package com.pavlig43.retromeetdata.friendRepository.api

import com.pavlig43.retromeetdata.common.FRIEND_ID
import com.pavlig43.retromeetdata.common.FriendStatus
import com.pavlig43.retromeetdata.common.PAGE_SIZE
import com.pavlig43.retromeetdata.common.UPDATE_AT
import com.pavlig43.retromeetdata.common.USER_ID
import com.pavlig43.retromeetdata.searchuserRepository.model.SearchUsersResponse
import com.pavlig43.retromeetdata.utils.api.createRetrofitApi
import kotlinx.serialization.json.Json
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Response
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

private const val FRIEND_STATUS = "friend_status"

private const val ROUTE = "friends"
interface FriendApi {
    @DELETE(ROUTE)
    suspend fun deleteFriend(
        @Query(USER_ID) userId:Int,
        @Query(FRIEND_ID) friendId:Int,
    ): Response<Unit>

    @POST(ROUTE)
    suspend fun acceptFriendRequest(
        @Query(USER_ID) userId:Int,
        @Query(FRIEND_ID) friendId:Int,
    ): Response<Unit>

    @GET(ROUTE)
    suspend fun getFriends(
        @Query(UPDATE_AT) updateAt: Long,
        @Query(PAGE_SIZE) pageSize: Int = 10,
        @Query(USER_ID) userId: Int? = null,
        @Query(FRIEND_STATUS) friendStatus: FriendStatus,
    ): Response<SearchUsersResponse>

}




fun FriendApi(
    baseUrl: String,
    client: OkHttpClient? = null,

    interceptor: Interceptor? = null,
    json: Json = Json,
) = createRetrofitApi<FriendApi>(
    baseUrl,
    client,
    interceptor,
    json
)