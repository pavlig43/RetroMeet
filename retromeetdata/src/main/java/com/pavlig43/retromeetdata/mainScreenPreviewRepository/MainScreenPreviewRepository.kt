package com.pavlig43.retromeetdata.mainScreenPreviewRepository

import com.pavlig43.retromeetcommon.Logger
import com.pavlig43.retromeetdata.mainScreenPreviewRepository.api.MainScreenPreviewApi
import com.pavlig43.retromeetdata.mainScreenPreviewRepository.model.MainScreenPreviewResponse
import com.pavlig43.retromeetdata.utils.requestResult.RequestResult
import com.pavlig43.retromeetdata.utils.requestResult.toRequestResult
import com.pavlig43.retromeetdata.utils.requestResult.toRequestResultWithMap
import jakarta.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class MainScreenPreviewRepository @Inject constructor(
    private val api: MainScreenPreviewApi,
    private val logger: Logger
) {

    fun observeMainScreenPreview(loginId: Int): Flow<RequestResult<MainScreenPreviewResponse>> {

        return flow { emit(getMainScreenPreviewFromServer(loginId)) }
    }

    private suspend fun getMainScreenPreviewFromServer(loginId: Int): RequestResult<MainScreenPreviewResponse> {
        val response = api.getMainScreenPreview(loginId)
            .toRequestResult(
                TAG,
                logger,

            )
        return response
    }

    companion object {
        private const val TAG = "MainScreenPreviewRepository"
    }
}
