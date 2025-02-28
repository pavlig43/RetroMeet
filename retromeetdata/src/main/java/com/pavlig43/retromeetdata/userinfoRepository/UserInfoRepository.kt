package com.pavlig43.retromeetdata.userinfoRepository

import com.pavlig43.retromeetcommon.Logger
import com.pavlig43.retromeetdata.DateStoreSettings
import com.pavlig43.retromeetdata.searchuserRepository.model.FriendPreviewResponse
import com.pavlig43.retromeetdata.userinfoRepository.api.UserInfoApi
import com.pavlig43.retromeetdata.userinfoRepository.model.UserInfoResponse
import com.pavlig43.retromeetdata.utils.database.RetromeetDataBase
import com.pavlig43.retromeetdata.utils.requestResult.RequestResult
import com.pavlig43.retromeetdata.utils.requestResult.toRequestResult
import jakarta.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map

class UserInfoRepository @Inject constructor(
    private val dataBase: RetromeetDataBase,
    private val dataStore:DateStoreSettings,
    private val api: UserInfoApi,
    private val logger: Logger
) {
    fun observeUserInfo( friendId: Int): Flow<RequestResult<UserInfoResponse>> {
        return dataStore.userId.map { userId->
            getUserInfoFromServer(userId, friendId)
        }
//        return flow { emit(getUserInfoFromServer(userId, friendId)) }
    }

    private suspend fun getUserInfoFromServer(
        userId: Int,
        friendId: Int
    ): RequestResult<UserInfoResponse> {
        val requestResult = api.getUserInfo(userId, friendId)
            .toRequestResult(
                TAG,
                logger
            )
        if (requestResult is RequestResult.Success) {
            requestResult.data?.let {
                dataBase.searchDao.upsertUser(it.toFriendPreviewResponse(userId))
            }
        }
        return requestResult
    }

    companion object {
        const val TAG = "UserInfoRepository"
    }

    private fun UserInfoResponse.toFriendPreviewResponse(userId: Int): FriendPreviewResponse {
        return FriendPreviewResponse(
            userId = userId,
            friendId = resume.userId,
            gender = resume.gender,
            name = resume.name,
            city = resume.city,
            dateOfBirth = resume.dateOfBirth,
            mainPhotoPath = resume.mainPhotoPath,
            friendStatus = friendStatus,
            isOnline = lastVisit.time == null)
    }
}

