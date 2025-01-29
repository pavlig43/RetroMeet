package com.pavlig43.features.searchusersresult

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.navigation.toRoute
import com.pavlig43.features.searchuser.model.SearchUserRequestUi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject


@HiltViewModel
class SearchUserResultViewModel @Inject constructor(
    saveStateHandle: SavedStateHandle
): ViewModel() {
//    private val a = checkNotNull(saveStateHandle.toRoute<SearchUserRequestUi>())

    private val _route = MutableStateFlow(SearchUserRequestUi())
    val route = _route.asStateFlow()
    val map = mutableMapOf<String, Any?>()


    init {
        val keys = saveStateHandle.keys()

        keys.forEach { key ->
            val value = saveStateHandle.get<Any?>(key)
            value?.let { map[key] = value }
            Log.d("keyValue","$key : $value")
        }
    }


}
