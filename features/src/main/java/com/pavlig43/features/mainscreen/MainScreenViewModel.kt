package com.pavlig43.features.mainscreen

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.pavlig43.features.common.enumui.mappers.toGenderUI
import com.pavlig43.features.mainscreen.model.MainScreenPreviewUi
import com.pavlig43.features.mainscreen.route.MainScreenRoute
import com.pavlig43.features.utils.getCountYearsToCurrentYear
import com.pavlig43.retromeetdata.mainScreenPreviewRepository.MainScreenPreviewRepository
import com.pavlig43.retromeetdata.mainScreenPreviewRepository.model.MainScreenPreviewResponse
import com.pavlig43.retromeetdata.utils.requestResult.RequestResult
import com.pavlig43.retromeetdata.utils.requestResult.mapTo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class MainScreenViewModel @Inject constructor(
    saveStateHandle: SavedStateHandle,
    mainScreenPreviewRepository: MainScreenPreviewRepository,
) : ViewModel() {
    private val loginId = checkNotNull(saveStateHandle.toRoute<MainScreenRoute>()).loginId


    val userInfoPreviewState = mainScreenPreviewRepository.observeMainScreenPreview(loginId)
        .map { result -> result.mapTo { it.toMainScreenPreviewUi() }.toUserInfoPreviewState() }
        .stateIn(
            viewModelScope,
            SharingStarted.Lazily,
            UserInfoPreviewState.Loading
        )



    fun goToScreen(action: (Int) -> Unit) {
        action(loginId)
    }

    val unreadMessages = MutableStateFlow(0)
    val requestToFriend = MutableStateFlow(0)
}

sealed class UserInfoPreviewState {
    object Loading : UserInfoPreviewState()
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
        loginId = loginId,
        mainPhotoPath = mainPhotoPath,
        name = name,
        age = dateOfBirth.getCountYearsToCurrentYear()
    )
}
