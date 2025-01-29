package com.pavlig43.features.register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pavlig43.features.WELCOME
import com.pavlig43.features.register.model.RegisterRequestUi
import com.pavlig43.features.register.model.RegisterResponseUi
import com.pavlig43.retromeetdata.registerRepository.RegisterRepository
import com.pavlig43.retromeetdata.registerRepository.model.RegisterRequest
import com.pavlig43.retromeetdata.registerRepository.model.RegisterResponse
import com.pavlig43.retromeetdata.utils.requestResult.RequestResult
import com.pavlig43.retromeetdata.utils.requestResult.mapTo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val registerRepository: RegisterRepository,
) :
    ViewModel() {

    private val _registerRequestUi = MutableStateFlow(RegisterRequestUi())
    val registerUi = _registerRequestUi.asStateFlow()

    private val _registerState: MutableStateFlow<RegisterState> =
        MutableStateFlow(RegisterState.Start)
    val registerState = _registerState.asStateFlow()

    val buttonState = _registerState.map { it is RegisterState.Loading }
        .stateIn(
            viewModelScope,
            SharingStarted.Eagerly,
            false
        )

    fun onLoginChanged(login: String) {
        _registerRequestUi.update { it.copy(login = login) }
    }

    fun onPasswordChanged(password: String) {
        _registerRequestUi.update { it.copy(password = password) }
    }

    fun tryRegister(onResult: (String) -> Unit) {
        viewModelScope.launch {

            _registerState.update { RegisterState.Loading }
            val result =
                registerRepository.tryRegister(_registerRequestUi.value.toRegisterRequest())
                    .mapTo { it.toRegisterResponseUi() }
            _registerState.update { result.toRegisterState() }
            onResult(registerState.value.message ?: "")
        }
    }
}

sealed class RegisterState(open val message: String? = null) {
    object Start : RegisterState()
    object Loading : RegisterState()
    data class Success(val loginId: Int, override val message: String) : RegisterState()
    data class Error(override val message: String) : RegisterState()
}

private fun RequestResult<RegisterResponseUi>.toRegisterState(): RegisterState {
    return when (val result = this) {
        is RequestResult.Error -> RegisterState.Error(result.throwable?.message ?: "Unknown error")
        is RequestResult.InProgress -> RegisterState.Loading
        is RequestResult.Success -> result.data?.loginId?.let { RegisterState.Success(it, WELCOME) }
            ?: RegisterState.Error("Unknown error")
    }
}

private fun RegisterRequestUi.toRegisterRequest() = RegisterRequest(login, password)
private fun RegisterResponse.toRegisterResponseUi() = RegisterResponseUi(loginId)
