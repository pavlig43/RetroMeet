package com.pavlig43.retromeetdata.searchUser.api

import com.pavlig43.retromeetdata.common.PAGE
import com.pavlig43.retromeetdata.common.PAGE_SIZE
import com.pavlig43.retromeetdata.common.USER_ID
import com.pavlig43.retromeetdata.searchUser.model.SearchUserFilterRequest
import com.pavlig43.retromeetdata.searchUser.model.SearchUsersResponse
import com.pavlig43.retromeetdata.utils.api.createRetrofitApi
import okhttp3.OkHttpClient
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Query

interface SearchUserApi {
    @POST("users/search")
    suspend fun searchUsers(
        @Query(PAGE) page: Int,
        @Query(PAGE_SIZE) pageSize: Int = 10,
        @Query(USER_ID) userId: Int? = null,
        @Body searchUserFilterRequest: SearchUserFilterRequest,

    ): Response<SearchUsersResponse>
}

fun SearchUserApi(
    baseUrl: String,
    client: OkHttpClient? = null,
) = createRetrofitApi<SearchUserApi>(
    baseUrl,
    client,

)
