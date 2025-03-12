package com.pavlig43.retromeetdata.utils.requestResult

import com.pavlig43.retromeetcommon.Logger
import com.pavlig43.retromeetdata.ERROR_FROM_SERVER
import retrofit2.Response

internal fun <I, O> Response<I>.toRequestResultWithMap(
    logTag: String,
    logger: Logger,
    mapper: (I) -> O,
): RequestResult<O> {
    return this.toRequestResult(
        logTag,
        logger,

    ).mapTo(mapper)
}
internal fun <T> Response<T>.toRequestResult(
    logTag: String,
    logger: Logger,

): RequestResult<T> {
    val result = if (isSuccessful) {
        RequestResult.Success<T>(body())
    } else {
        val errorCode = this.code()
        val errorMessage = this.errorBody()?.string() ?: ""
        val throwable = Throwable(message = "$errorCode : $errorMessage")
        RequestResult.Error<T>(body(), throwable)
    }

    result.sendDLog(
        logTag,
        ERROR_FROM_SERVER,
        logger
    )
    return result
}

private fun <T> RequestResult<T>.sendDLog(
    tag: String,
    from: String,
    logger: Logger
) {
    if (this is RequestResult.Error) {
        logger.d(tag, "$from:$throwable")
    }
}
