package com.pavlig43.retromeetdata.dialog.api

import com.pavlig43.retromeetdata.common.PAGE
import com.pavlig43.retromeetdata.common.PAGE_SIZE
import com.pavlig43.retromeetdata.common.USER_ID
import com.pavlig43.retromeetdata.dialog.model.DialogsResponse
import com.pavlig43.retromeetdata.utils.api.createRetrofitApi
import okhttp3.OkHttpClient
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

private const val ROUTE = "dialogs"

interface DialogApi {
    @GET(ROUTE)
    suspend fun getDialogs(
        @Query(PAGE) page: Int,
        @Query(PAGE_SIZE) pageSize: Int,
        @Query(USER_ID) userId: Int
    ): Response<DialogsResponse>
}
fun DialogApi(
    baseUrl: String,
    client: OkHttpClient? = null,

) = createRetrofitApi<DialogApi>(
    baseUrl,
    client,

)
