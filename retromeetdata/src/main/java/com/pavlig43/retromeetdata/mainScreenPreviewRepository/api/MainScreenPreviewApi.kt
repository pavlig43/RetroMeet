package com.pavlig43.retromeetdata.mainScreenPreviewRepository.api

import com.pavlig43.retromeetdata.mainScreenPreviewRepository.model.MainScreenPreviewResponse
import com.pavlig43.retromeetdata.utils.api.createRetrofitApi
import kotlinx.serialization.json.Json
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface MainScreenPreviewApi {
    @GET("mainscreenpreview/{loginId}")
     suspend fun getMainScreenPreview(
        @Path("loginId") loginId: Int
    ): Response<MainScreenPreviewResponse>
}
fun MainScreenPreviewApi(
    baseUrl: String,
    client: OkHttpClient? = null,
    interceptor: Interceptor? = null,
    json: Json = Json,
) = createRetrofitApi<MainScreenPreviewApi>(
    baseUrl,
    client,
    interceptor,
    json
)
