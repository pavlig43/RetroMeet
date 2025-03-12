package com.pavlig43.retromeetdata.login.api

import com.pavlig43.retromeetdata.login.model.LoginRequest
import com.pavlig43.retromeetdata.login.model.LoginResponse
import com.pavlig43.retromeetdata.utils.api.createRetrofitApi
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
) = createRetrofitApi<LoginApi>(
    baseUrl,
    client,
)
