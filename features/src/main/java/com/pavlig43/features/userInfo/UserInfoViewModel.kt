package com.pavlig43.features.userInfo

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.pavlig43.features.common.enumui.mappers.toFriendStatus
import com.pavlig43.features.common.enumui.mappers.toFriendStatusUi
import com.pavlig43.features.common.enumui.model.FriendStatusUi
import com.pavlig43.features.userInfo.model.UserInfoUi
import com.pavlig43.features.userInfo.nested.resume.toResumeUi
import com.pavlig43.features.userInfo.route.UserInfoRoute
import com.pavlig43.features.utils.convertToDateTime
import com.pavlig43.retromeetdata.friendRepository.FriendRepository
import com.pavlig43.retromeetdata.userinfoRepository.UserInfoRepository
import com.pavlig43.retromeetdata.userinfoRepository.model.UserInfoResponse
import com.pavlig43.retromeetdata.utils.requestResult.RequestResult
import com.pavlig43.retromeetdata.utils.requestResult.mapTo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserInfoViewModel @Inject constructor(
    saveStateHandle: SavedStateHandle,
    userInfoRepository: UserInfoRepository,
    private val friendRepository: FriendRepository,

    ) : ViewModel(
) {
    private val friendId = checkNotNull(saveStateHandle.toRoute<UserInfoRoute>()).userId

    private val refreshTrigger = MutableSharedFlow<Unit>(extraBufferCapacity = 1)



    @OptIn(ExperimentalCoroutinesApi::class)
    val userInfo: StateFlow<UserInfoState> = refreshTrigger.onStart { emit(Unit) }.flatMapLatest {
        userInfoRepository.observeUserInfo( friendId)
            .map { result -> result.mapTo { it.toUserInfoUi() }.toUserInfoState() }
    }.stateIn(
        viewModelScope,
        SharingStarted.Eagerly,
        UserInfoState.Loading
    )

    fun changeFriendStatus(friendStatusUi: FriendStatusUi) {
        viewModelScope.launch {
            val result:RequestResult<Unit> = friendRepository.changeFriendStatus(
                friendId,
                friendStatusUi.toFriendStatus()
            )

            handleRequestResultFriend(result)
        }

    }

    private suspend fun handleRequestResultFriend(result: RequestResult<Unit>){
        when (result){
            is RequestResult.Error -> {}
            is RequestResult.InProgress -> {}
            is RequestResult.Success -> refreshTrigger.emit(Unit)
        }
    }

}


sealed class UserInfoState {
    data object Loading : UserInfoState()
    data class Success(val data: UserInfoUi) : UserInfoState()
    data class Error(val message: String) : UserInfoState()
}

private fun RequestResult<UserInfoUi>.toUserInfoState(): UserInfoState {
    return when (val result = this) {
        is RequestResult.Error -> UserInfoState.Error(result.throwable?.message ?: "Unknown error")
        is RequestResult.InProgress -> UserInfoState.Loading
        is RequestResult.Success -> result.data?.let {
            UserInfoState.Success(it)
        } ?: UserInfoState.Error("Nothing came")
    }
}

private fun UserInfoResponse.toUserInfoUi(): UserInfoUi {
    return UserInfoUi(
        resumeUi = this.resume.toResumeUi(),
        friendStatusUi = this.friendStatus.status.toFriendStatusUi(),
        lastVisit = this.lastVisit.time.convertToDateTime()
    )
}

