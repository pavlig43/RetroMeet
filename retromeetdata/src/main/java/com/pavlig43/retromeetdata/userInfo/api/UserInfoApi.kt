package com.pavlig43.retromeetdata.userInfo.api

import com.pavlig43.retromeetdata.common.FRIEND_ID
import com.pavlig43.retromeetdata.common.USER_ID
import com.pavlig43.retromeetdata.userInfo.model.UserInfoResponse
import com.pavlig43.retromeetdata.utils.api.createRetrofitApi
import okhttp3.OkHttpClient
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface UserInfoApi {
    @GET("user/info")
    suspend fun getUserInfo(
        @Query(USER_ID) userId: Int,
        @Query(FRIEND_ID) friendId: Int
    ): Response<UserInfoResponse>
}
fun UserInfoApi(
    baseUrl: String,
    client: OkHttpClient? = null,

) = createRetrofitApi<UserInfoApi>(
    baseUrl,
    client,

)
