package com.pavlig43.features.searchusersresult

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.pavlig43.features.common.enumui.mappers.toShortGenderUI
import com.pavlig43.features.searchusersresult.model.SearchUserPreviewUi
import com.pavlig43.features.utils.getCountYearsToCurrentYear
import com.pavlig43.retromeetdata.searchuserRepository.SearchUserRepository
import com.pavlig43.retromeetdata.searchuserRepository.model.FriendPreviewResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject


@HiltViewModel
class SearchUserResultViewModel @Inject constructor(
    searchUserRepository: SearchUserRepository,
) : ViewModel() {


    val result: StateFlow<PagingData<SearchUserPreviewUi>> =
        searchUserRepository.getSearchUsers()
            .map { pagingData ->
                pagingData.map { response -> response.toSearchUserPreviewResponseUi() }
            }.cachedIn(viewModelScope)
            .stateIn(viewModelScope, SharingStarted.Eagerly, PagingData.empty())

}


fun FriendPreviewResponse.toSearchUserPreviewResponseUi(): SearchUserPreviewUi {
    return SearchUserPreviewUi(
        gender = gender.toShortGenderUI(),
        userId = friendId,
        mainPhotoPath = mainPhotoPath,
        name = name,
        city = city,
        age = dateOfBirth.getCountYearsToCurrentYear(),
        isOnline = isOnline,
    )
}

