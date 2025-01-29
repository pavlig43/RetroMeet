package com.pavlig43.retromeetdata.userinfoRepository.api


import com.pavlig43.retromeetdata.userinfoRepository.model.UserInfoResponse
import com.pavlig43.retromeetdata.userinfoRepository.model.UserInfoUpdateRequest
import com.pavlig43.retromeetdata.utils.api.createRetrofitApi
import kotlinx.serialization.json.Json
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

// import retrofit2.ad

interface ResumeApi {
    @GET("userinfo/{login}")
    suspend fun getUserInfo(
        @Path("login") login: Int
    ): Response<UserInfoResponse>

    @POST("userinfo")
    suspend fun updateUserInfo(@Body userInfoRequest: UserInfoUpdateRequest): Response<Unit>
}
fun ResumeApi(
    baseUrl: String,
    client: OkHttpClient? = null,
    interceptor: Interceptor? = null,
    json: Json = Json,
) = createRetrofitApi<ResumeApi>(
    baseUrl,
    client,
    interceptor,
    json
)
