package com.pavlig43.features.dialog

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.pavlig43.features.dialog.model.DialogInfoUi
import com.pavlig43.features.dialog.model.DialogResponseUi
import com.pavlig43.features.dialog.model.DialogUserPreviewUi
import com.pavlig43.retromeetdata.dialog.DialogsRepository
import com.pavlig43.retromeetdata.dialog.model.DialogInfo
import com.pavlig43.retromeetdata.dialog.model.DialogResponse
import com.pavlig43.retromeetdata.dialog.model.DialogUserPreview
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject


@HiltViewModel
class DialogsViewModel @Inject constructor(
    private val dialogsRepository: DialogsRepository,
):ViewModel() {

    val dialogs = dialogsRepository.getDialogs()
        .map { pagingData -> pagingData.map { it.toDialogResponseUi() } }
        .cachedIn(viewModelScope)
        .stateIn(viewModelScope, SharingStarted.Lazily, PagingData.empty())
    val userInfo = dialogs.map {

    }

}
private fun DialogResponse.toDialogResponseUi(): DialogResponseUi {
    return DialogResponseUi(
        localId = localId,
        info = info.toDialogInfoUi(),
        userInfo = userInfo.toDialogUserPreviewUi()
    )
}
private fun DialogInfo.toDialogInfoUi(): DialogInfoUi {
    return DialogInfoUi(
        serverId = serverId,
        userId = userId,
        friendId = friendId,
        updatedAt = updatedAt
    )
}
private fun DialogUserPreview.toDialogUserPreviewUi(): DialogUserPreviewUi {
    return DialogUserPreviewUi(
        name = name?:"_",
        mainPhoto = mainPhoto,
        isOnline = isOnline
    )
}