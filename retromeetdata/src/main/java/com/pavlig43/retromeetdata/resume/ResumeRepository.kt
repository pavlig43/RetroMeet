package com.pavlig43.retromeetdata.resume

import com.pavlig43.retromeetcommon.Logger
import com.pavlig43.retromeetdata.DateStoreSettings
import com.pavlig43.retromeetdata.resume.api.ResumeApi
import com.pavlig43.retromeetdata.resume.model.ResumeResponse
import com.pavlig43.retromeetdata.resume.model.ResumeUpdateRequest
import com.pavlig43.retromeetdata.utils.requestResult.RequestResult
import com.pavlig43.retromeetdata.utils.requestResult.toRequestResult
import jakarta.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

class ResumeRepository @Inject constructor(
    private val resumeApi: ResumeApi,
    private val dataStore: DateStoreSettings,
    private val logger: Logger,
) {
    suspend fun getResume(): RequestResult<ResumeResponse> {
        val userId = dataStore.userId.first()
        val userInfo = resumeFromServer(userId)
        return userInfo
    }
    fun observeResume(): Flow<RequestResult<ResumeResponse>> {
        return dataStore.userId.map { userId ->
            resumeFromServer(userId)
        }
    }

    private suspend fun resumeFromServer(userId: Int): RequestResult<ResumeResponse> {
        val apiResponse: RequestResult<ResumeResponse> = resumeApi.getResume(userId)
            .toRequestResult(
                TAG,
                logger,
            )
        return apiResponse
    }
    suspend fun updateResume(resumeUpdater: ResumeUpdateRequest) {
        resumeApi.updateResume(resumeUpdater)
    }

    companion object {
        const val TAG = "ResumeRepository"
    }
}
