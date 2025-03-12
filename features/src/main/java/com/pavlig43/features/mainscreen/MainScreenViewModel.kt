package com.pavlig43.features.mainscreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pavlig43.features.common.enumui.mappers.toGenderUI
import com.pavlig43.features.mainscreen.model.MainScreenPreviewUi
import com.pavlig43.features.utils.getCountYearsToCurrentYear
import com.pavlig43.retromeetdata.friend.ObserveFriendRequestRepository
import com.pavlig43.retromeetdata.mainScreenPreview.MainScreenPreviewRepository
import com.pavlig43.retromeetdata.mainScreenPreview.model.MainScreenPreviewResponse
import com.pavlig43.retromeetdata.dialog.ObserveUnreadDialogRepository
import com.pavlig43.retromeetdata.utils.requestResult.RequestResult
import com.pavlig43.retromeetdata.utils.requestResult.mapTo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class MainScreenViewModel @Inject constructor(
    mainScreenPreviewRepository: MainScreenPreviewRepository,
    observeFriendRequestRepository: ObserveFriendRequestRepository,
    observeUnreadDialogRepository: ObserveUnreadDialogRepository,
) : ViewModel() {

    val userInfoPreviewState = mainScreenPreviewRepository.observeMainScreenPreview()
        .map { result -> result.mapTo { it.toMainScreenPreviewUi() }.toUserInfoPreviewState() }
        .stateIn(
            viewModelScope,
            SharingStarted.Eagerly,
            UserInfoPreviewState.Loading
        )

    val requestToFriend = observeFriendRequestRepository.observeFriendRequest()
    .stateIn(
        viewModelScope,
        SharingStarted.Eagerly,
        0
    )
    val unreadDialogs = observeUnreadDialogRepository.observeUnreadMessageWithSender()
        .map {response-> response.unreadDialogs.sumOf { it.count } }
        .stateIn(
            viewModelScope,
            SharingStarted.Eagerly,
            0

        )


    fun goToScreen(action: (Int) -> Unit) {
        action(1)
    }

}

sealed class UserInfoPreviewState {
    data object Loading : UserInfoPreviewState()
    data class Success(val data: MainScreenPreviewUi) : UserInfoPreviewState()
    data class Error(val errorMessage: String) : UserInfoPreviewState()
}

private fun <T> RequestResult<T>.toUserInfoPreviewState(): UserInfoPreviewState {
    return when (val result = this) {
        is RequestResult.Error -> UserInfoPreviewState.Error(
            result.throwable?.message ?: "Unknown error"
        )

        is RequestResult.InProgress -> UserInfoPreviewState.Loading
        is RequestResult.Success -> UserInfoPreviewState.Success(result.data as MainScreenPreviewUi)
    }
}

private fun MainScreenPreviewResponse.toMainScreenPreviewUi(): MainScreenPreviewUi {
    return MainScreenPreviewUi(
        gender = gender.toGenderUI(),
        userId = userId,
        mainPhotoPath = mainPhotoPath,
        name = name,
        age = dateOfBirth.getCountYearsToCurrentYear()
    )
}
