package com.pavlig43.retromeetdata.searchuserRepository.api

import com.pavlig43.retromeetdata.common.FriendStatus
import com.pavlig43.retromeetdata.common.LAST_ID
import com.pavlig43.retromeetdata.common.PAGE_SIZE
import com.pavlig43.retromeetdata.common.USER_ID
import com.pavlig43.retromeetdata.searchuserRepository.model.SearchUserFilterRequest
import com.pavlig43.retromeetdata.searchuserRepository.model.SearchUsersResponse
import com.pavlig43.retromeetdata.utils.api.createRetrofitApi
import kotlinx.serialization.json.Json
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query



interface SearchUserApi {
    @POST("users/search")
    suspend fun searchUsers(
        @Query(LAST_ID) lastId: Int,
        @Query(PAGE_SIZE) pageSize: Int = 10,
        @Query(USER_ID) userId: Int? = null,
        @Body searchUserFilterRequest: SearchUserFilterRequest,

        ): Response<SearchUsersResponse>
}


fun SearchUserApi(
    baseUrl: String,
    client: OkHttpClient? = null,
    interceptor: Interceptor? = null,
    json: Json = Json,
) = createRetrofitApi<SearchUserApi>(
    baseUrl,
    client,
    interceptor,
    json
)
