package com.pavlig43.retromeetdata.utils.api

import okhttp3.Interceptor
import okhttp3.Response
import okhttp3.ResponseBody.Companion.toResponseBody
import okhttp3.logging.HttpLoggingInterceptor.Logger

class DefaultInterceptor(
    private val logTag: String,

) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val logger = Logger.DEFAULT
        val originalRequest = chain.request()
        logger.log("$logTag: Request Url: ${originalRequest.url}")
        originalRequest.body?.let { logger.log("$logTag - Request Body: ${it.contentType()}") }

        val response = chain.proceed(originalRequest)
        response.body?.let {
            val responseBody = it.string()
            logger.log("$logTag - Response Body: $responseBody")
            return response.newBuilder()
                .body(responseBody.toResponseBody(it.contentType()))
                .build()
        }

        return response
    }
}
