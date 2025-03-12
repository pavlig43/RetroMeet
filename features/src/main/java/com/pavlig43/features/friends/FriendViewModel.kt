package com.pavlig43.features.friends

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.pavlig43.features.common.enumui.mappers.toFriendStatus
import com.pavlig43.features.common.enumui.model.FriendStatusUi
import com.pavlig43.features.searchusersresult.model.SearchUserPreviewUi
import com.pavlig43.features.searchusersresult.toSearchUserPreviewResponseUi
import com.pavlig43.retromeetdata.friend.FriendRepository
import com.pavlig43.retromeetdata.friend.ObserveFriendRequestRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import javax.inject.Inject


@HiltViewModel
class FriendViewModel @Inject constructor(
    private val friendsRepository: FriendRepository,
    observeFriendRequestRepository: ObserveFriendRequestRepository,
) : ViewModel() {
    private val _friendStatus: MutableStateFlow<FriendStatus> =
        MutableStateFlow(FriendStatus(FriendStatusUi.FRIEND))

    val requestToFriend = observeFriendRequestRepository.observeFriendRequest()
        .onEach {
            if (_friendStatus.value.status == FriendStatusUi.REQUEST_MINUS) _friendStatus.update {
                FriendStatus(
                    FriendStatusUi.REQUEST_MINUS
                )
            }
        }
        .stateIn(
            viewModelScope,
            SharingStarted.Lazily,
            0
        )



    @OptIn(ExperimentalCoroutinesApi::class)
    val result: StateFlow<PagingData<SearchUserPreviewUi>> =
        this._friendStatus.flatMapLatest { status ->
            friendsRepository.getFriends(status.status.toFriendStatus())
                .map { users ->
                    users.map { it.toSearchUserPreviewResponseUi() }
                }
        }.cachedIn(viewModelScope)
            .stateIn(viewModelScope, SharingStarted.Lazily, PagingData.empty())


    fun changeTab(friendStatus: FriendStatusUi) {
        this._friendStatus.update { FriendStatus(friendStatus) }
    }


}

private class FriendStatus(val status: FriendStatusUi)

