package com.pavlig43.retromeetdata.loginRepository.api


import com.pavlig43.retromeetdata.loginRepository.model.LoginRequest
import com.pavlig43.retromeetdata.loginRepository.model.LoginResponse
import com.pavlig43.retromeetdata.utils.api.createRetrofitApi
import kotlinx.serialization.json.Json
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface LoginApi {
    @POST("login")
    suspend fun login(@Body loginRequest: LoginRequest): Response<LoginResponse>
}

fun LoginApi(
    baseUrl: String,
    client: OkHttpClient? = null,
    interceptor: Interceptor? = null,
    json: Json = Json,
) = createRetrofitApi<LoginApi>(
    baseUrl,
    client,
    interceptor,
    json
)
