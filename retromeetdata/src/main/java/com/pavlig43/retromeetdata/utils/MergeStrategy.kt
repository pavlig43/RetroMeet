package com.pavlig43.retromeetdata.utils

import com.pavlig43.retromeetdata.utils.requestResult.RequestResult
import com.pavlig43.retromeetdata.utils.requestResult.RequestResult.Error
import com.pavlig43.retromeetdata.utils.requestResult.RequestResult.InProgress
import com.pavlig43.retromeetdata.utils.requestResult.RequestResult.Success

interface MergeStrategy<E> {
    fun merge(right: E, left: E): E
}
internal class RequestResultMergeStrategy<T> : MergeStrategy<RequestResult<T>> {
    @Suppress("CyclomaticComplexMethod")
    override fun merge(
        right: RequestResult<T>,
        left: RequestResult<T>
    ): RequestResult<T> {
        return when {
            right is InProgress && left is InProgress -> merge(right, left)
            right is InProgress && left is Success -> merge(right, left)
            right is InProgress && left is Error -> merge(right, left)
            right is Success && left is InProgress -> merge(right, left)
            right is Success && left is Success -> merge(right, left)
            right is Success && left is Error -> merge(right, left)
            right is Error && left is InProgress -> merge(right, left)
            right is Error && left is Success -> merge(right, left)
            right is Error && left is Error -> merge(right, left)
            else -> error("Unimplemented branch right=$right & left=$left")
        }
    }
    private fun merge(
        cache: InProgress<T>,
        server: InProgress<T>
    ): RequestResult<T> {
        return when {
            server.data != null -> InProgress(server.data)
            else -> InProgress(cache.data)
        }
    }

    @Suppress("UnusedParameter")
    fun merge(
        cache: InProgress<T>,
        server: Success<T>
    ): RequestResult<T> {
        return InProgress(server.data)
    }

    fun merge(
        cache: InProgress<T>,
        server: Error<T>
    ): RequestResult<T> {
        return Error(server.data ?: cache.data, server.throwable)
    }

    @Suppress("UnusedParameter")
    private fun merge(
        cache: Success<T>,
        server: InProgress<T>
    ): RequestResult<T> {
        return InProgress(cache.data)
    }

    @Suppress("UnusedParameter")
    private fun merge(
        cache: Success<T>,
        server: Success<T>
    ): RequestResult<T> {
        return Success(server.data)
    }
    private fun merge(
        cache: Success<T>,
        server: Error<T>
    ): RequestResult<T> {
        return Error(cache.data, server.throwable)
    }

    @Suppress("UnusedParameter")
    private fun merge(
        cache: Error<T>,
        server: InProgress<T>
    ): RequestResult<T> {
        return Error(server.data)
    }

    @Suppress("UnusedParameter")
    private fun merge(
        cache: Error<T>,
        server: Success<T>
    ): RequestResult<T> {
        return InProgress(server.data)
    }
    private fun merge(
        cache: Error<T>,
        server: Error<T>
    ): RequestResult<T> {
        return Error(server.data ?: cache.data, server.throwable)
    }
}
