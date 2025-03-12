package com.pavlig43.retromeetdata.register.api

import com.pavlig43.retromeetdata.register.model.RegisterRequest
import com.pavlig43.retromeetdata.register.model.RegisterResponse
import com.pavlig43.retromeetdata.utils.api.createRetrofitApi
import okhttp3.OkHttpClient
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface RegisterApi {
    @POST("register")
    suspend fun register(@Body registerRequest: RegisterRequest): Response<RegisterResponse>
}

fun RegisterApi(
    baseUrl: String,
    client: OkHttpClient? = null,
) = createRetrofitApi<RegisterApi>(
    baseUrl,
    client,
)
