package com.pavlig43.retromeetdata.userinfoRepository

import com.pavlig43.retromeetcommon.Logger
import com.pavlig43.retromeetdata.userinfoRepository.api.ResumeApi
import com.pavlig43.retromeetdata.userinfoRepository.model.UserInfoResponse
import com.pavlig43.retromeetdata.userinfoRepository.model.UserInfoUpdateRequest
import com.pavlig43.retromeetdata.utils.requestResult.RequestResult
import com.pavlig43.retromeetdata.utils.requestResult.toRequestResult
import jakarta.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class UserInfoRepository @Inject constructor(
    private val resumeApi: ResumeApi,
    private val logger: Logger,
) {
    suspend fun getUserInfo(loginId: Int): RequestResult<UserInfoResponse> {
        val userInfo = getUserInfoFromServer(loginId)
        return userInfo
    }
    fun observeUserInfo(loginId: Int): Flow<RequestResult<UserInfoResponse>> {
        return flow { emit(getUserInfoFromServer(loginId)) }
    }

    private suspend fun getUserInfoFromServer(loginId: Int): RequestResult<UserInfoResponse> {
        val apiResponse: RequestResult<UserInfoResponse> = resumeApi.getUserInfo(loginId)
            .toRequestResult(
                TAG,
                logger,
            )
        return apiResponse
    }
    suspend fun updateUserInfo(updaterUserInfo: UserInfoUpdateRequest) {
        resumeApi.updateUserInfo(updaterUserInfo)
    }

    companion object {
        const val TAG = "UserInfoRepository"
    }
}
