package com.pavlig43.retromeetdata.mainScreenPreview

import com.pavlig43.retromeetcommon.Logger
import com.pavlig43.retromeetdata.DateStoreSettings
import com.pavlig43.retromeetdata.mainScreenPreview.api.MainScreenPreviewApi
import com.pavlig43.retromeetdata.mainScreenPreview.model.MainScreenPreviewResponse
import com.pavlig43.retromeetdata.utils.requestResult.RequestResult
import com.pavlig43.retromeetdata.utils.requestResult.toRequestResult
import jakarta.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class MainScreenPreviewRepository @Inject constructor(
    private val dataStore: DateStoreSettings,
    private val api: MainScreenPreviewApi,
    private val logger: Logger
) {

    fun observeMainScreenPreview(): Flow<RequestResult<MainScreenPreviewResponse>> {
        return dataStore.userId.map { userId ->
            getMainScreenPreviewFromServer(userId)
        }
    }

    private suspend fun getMainScreenPreviewFromServer(userId: Int): RequestResult<MainScreenPreviewResponse> {
        val response = api.getMainScreenPreview(userId)
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
