package com.pavlig43.retromeetdata.registerRepository.api


import com.pavlig43.retromeetdata.registerRepository.model.RegisterRequest
import com.pavlig43.retromeetdata.registerRepository.model.RegisterResponse
import com.pavlig43.retromeetdata.utils.api.createRetrofitApi
import kotlinx.serialization.json.Json
import okhttp3.Interceptor
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
    interceptor: Interceptor? = null,
    json: Json = Json,
) = createRetrofitApi<RegisterApi>(
    baseUrl,
    client,
    interceptor,
    json
)
