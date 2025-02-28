package com.pavlig43.retromeetdata.resumeRepository.api


import com.pavlig43.retromeetdata.common.USER_ID
import com.pavlig43.retromeetdata.resumeRepository.model.ResumeResponse
import com.pavlig43.retromeetdata.resumeRepository.model.ResumeUpdateRequest
import com.pavlig43.retromeetdata.utils.api.createRetrofitApi
import kotlinx.serialization.json.Json
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

// import retrofit2.ad

private const val ROUTE = "resume"

interface ResumeApi {
    @GET(ROUTE)
    suspend fun getResume(
        @Query(USER_ID) userId: Int
    ): Response<ResumeResponse>

    @POST(ROUTE)
    suspend fun updateResume(@Body resumeRequest: ResumeUpdateRequest): Response<Unit>
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
