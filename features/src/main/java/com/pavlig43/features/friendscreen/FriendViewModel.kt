package com.pavlig43.features.friendscreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.pavlig43.features.common.enumui.mappers.toFriendStatus
import com.pavlig43.features.common.enumui.model.FriendStatusUi
import com.pavlig43.features.searchusersresult.toSearchUserPreviewResponseUi
import com.pavlig43.retromeetdata.friendRepository.FriendRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import javax.inject.Inject


@HiltViewModel
class FriendViewModel @Inject constructor(
    private val friendsRepository: FriendRepository
) : ViewModel() {
    private val _friendStatus = MutableStateFlow(FriendStatusUi.FRIEND)

    @OptIn(ExperimentalCoroutinesApi::class)
    val result = _friendStatus.flatMapLatest { friendStatus ->
        friendsRepository.getFriends(friendStatus.toFriendStatus())
            .map { users ->
                users.map { it.toSearchUserPreviewResponseUi() } }
    }.cachedIn(viewModelScope)
        .stateIn(viewModelScope, SharingStarted.Lazily, PagingData.empty())


    fun changeTab(friendStatus: FriendStatusUi) {
        _friendStatus.update { friendStatus }
    }


}

