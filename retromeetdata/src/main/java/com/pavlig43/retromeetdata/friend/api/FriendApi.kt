package com.pavlig43.retromeetdata.friend.api

import com.pavlig43.retromeetdata.common.FRIEND_ID
import com.pavlig43.retromeetdata.common.FriendStatus
import com.pavlig43.retromeetdata.common.PAGE
import com.pavlig43.retromeetdata.common.PAGE_SIZE
import com.pavlig43.retromeetdata.common.USER_ID
import com.pavlig43.retromeetdata.searchUser.model.SearchUsersResponse
import com.pavlig43.retromeetdata.utils.api.createRetrofitApi
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
        @Query(USER_ID) userId: Int,
        @Query(FRIEND_ID) friendId: Int,
    ): Response<Unit>

    @POST(ROUTE)
    suspend fun acceptFriendRequest(
        @Query(USER_ID) userId: Int,
        @Query(FRIEND_ID) friendId: Int,
    ): Response<Unit>

    @GET(ROUTE)
    suspend fun getFriends(
        @Query(PAGE) page: Int,
        @Query(PAGE_SIZE) pageSize: Int = 10,
        @Query(USER_ID) userId: Int? = null,
        @Query(FRIEND_STATUS) friendStatus: FriendStatus,
    ): Response<SearchUsersResponse>
}

fun FriendApi(
    baseUrl: String,
    client: OkHttpClient? = null,

) = createRetrofitApi<FriendApi>(
    baseUrl,
    client,

)
