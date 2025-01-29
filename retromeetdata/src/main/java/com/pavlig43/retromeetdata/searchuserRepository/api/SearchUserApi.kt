package com.pavlig43.retromeetdata.searchuserRepository.api

import com.pavlig43.retromeetdata.searchuserRepository.model.SearchUserRequest
import com.pavlig43.retromeetdata.searchuserRepository.model.SearchUsersResponse
import com.pavlig43.retromeetdata.utils.api.createRetrofitApi
import kotlinx.serialization.json.Json
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Query

interface SearchUserApi {
    @POST("searchuser")
    suspend fun getUsers(
        @Body searchUserRequest: SearchUserRequest,
        @Query("page") page: Int = 1,
        @Query("pageSize") pageSize: Int = 10,
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
