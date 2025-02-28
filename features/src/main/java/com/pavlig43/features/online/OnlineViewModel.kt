package com.pavlig43.features.online

import androidx.lifecycle.ViewModel
import com.pavlig43.retromeetdata.onlineRepository.OnlineRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class OnlineViewModel @Inject constructor(
    private val onlineRepository: OnlineRepository,
): ViewModel() {
    val online = onlineRepository.closeOldAndCreateNewWebSocket()



}

