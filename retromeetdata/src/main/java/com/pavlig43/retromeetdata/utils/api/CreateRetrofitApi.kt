package com.pavlig43.retromeetdata.utils.api

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.skydoves.retrofit.adapters.result.ResultCallAdapterFactory
import kotlinx.serialization.json.Json
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import retrofit2.Retrofit

internal inline fun <reified T> createRetrofitApi(
    baseUrl: String,
    client: OkHttpClient? = null,
    interceptor: Interceptor? = null,
    json: Json = Json,

): T {
    val contentType = "application/json".toMediaType()
    val converterFactory = json.asConverterFactory(contentType)

    val clientBuilder = (client?.newBuilder() ?: OkHttpClient.Builder())
    val modifiedClient = clientBuilder.run {
        if (interceptor != null) {
            addInterceptor(interceptor)
        } else {
            this.addInterceptor(
                DefaultInterceptor(T::class.java.simpleName)
            )
        }
    }
        .build()

    val retrofitBuilder = Retrofit.Builder()
        .baseUrl(baseUrl)
        .client(modifiedClient)
        .addCallAdapterFactory(ResultCallAdapterFactory.create())
        .addConverterFactory(converterFactory)
        .build()
    return retrofitBuilder.create(T::class.java)
}
