package com.pavlig43.retromeetdata.utils.requestResult

sealed class RequestResult<out E>(open val data: E? = null) {
    class InProgress<E>(data: E? = null) : RequestResult<E>(data)
    class Success<E>(data: E? = null) : RequestResult<E>(data)
    class Error<E>(
        data: E? = null,
        val throwable: Throwable? = null,

    ) : RequestResult<E>(data)
}

fun <I, O> RequestResult<I>.mapTo(mapper: (I) -> O): RequestResult<O> {
    return when (this) {
        is RequestResult.Success -> RequestResult.Success(data = data?.let(mapper))
        is RequestResult.Error -> RequestResult.Error(
            data = data?.let(mapper),
            throwable = throwable
        )

        is RequestResult.InProgress -> RequestResult.InProgress(data?.let(mapper))
    }
}
