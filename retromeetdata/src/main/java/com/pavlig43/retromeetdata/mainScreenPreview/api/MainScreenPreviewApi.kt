package com.pavlig43.retromeetdata.mainScreenPreview.api

import com.pavlig43.retromeetdata.common.USER_ID
import com.pavlig43.retromeetdata.mainScreenPreview.model.MainScreenPreviewResponse
import com.pavlig43.retromeetdata.utils.api.createRetrofitApi
import okhttp3.OkHttpClient
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface MainScreenPreviewApi {
    @GET("main-screen-preview")
    suspend fun getMainScreenPreview(
        @Query(USER_ID) userId: Int
    ): Response<MainScreenPreviewResponse>
}
fun MainScreenPreviewApi(
    baseUrl: String,
    client: OkHttpClient? = null,
) = createRetrofitApi<MainScreenPreviewApi>(
    baseUrl,
    client,
)
