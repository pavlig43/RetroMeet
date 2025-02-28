package com.pavlig43.features.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pavlig43.features.WELCOME
import com.pavlig43.features.login.model.LoginRequestUi
import com.pavlig43.features.login.model.LoginResponseUi
import com.pavlig43.retromeetdata.loginRepository.LoginRepository
import com.pavlig43.retromeetdata.loginRepository.model.LoginRequest
import com.pavlig43.retromeetdata.loginRepository.model.LoginResponse
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
class LoginViewModel @Inject constructor(
    private val loginRepository: LoginRepository

) : ViewModel() {
    private val _login = MutableStateFlow(LoginRequestUi())
    val login = _login.asStateFlow()

    private val _loginState: MutableStateFlow<LoginState> = MutableStateFlow(LoginState.Start)
    val loginState = _loginState.asStateFlow()
    val buttonState = _loginState.map { it is LoginState.Loading }
        .stateIn(
            viewModelScope,
            SharingStarted.Eagerly,
            false
        )


    fun onLoginChanged(login: String) {
        _login.update { it.copy(login = login) }
    }

    fun onPasswordChanged(password: String) {
        _login.update { it.copy(password = password) }
    }

    fun tryEnter(onResult: (String) -> Unit) {
        viewModelScope.launch {
            _loginState.update { LoginState.Loading }
            val result = loginRepository.tryLogin(_login.value.toLoginRequest())
                .mapTo { it.toLoginResponseUi() }
            _loginState.update { result.toLoginState() }
            onResult(loginState.value.message ?: "")
        }
    }

}

sealed class LoginState(open val message: String? = null) {
    data object Start : LoginState()
    data object Loading : LoginState()
    data class Success(val userId: Int, override val message: String) : LoginState(message)
    data class Error(override val message: String) : LoginState(message)
}

private fun RequestResult<LoginResponseUi>.toLoginState(): LoginState {
    return when (val result = this) {
        is RequestResult.Error -> LoginState.Error(result.throwable?.message ?: "Unknown error")
        is RequestResult.InProgress -> LoginState.Loading
        is RequestResult.Success -> result.data?.userId?.let { LoginState.Success(it, WELCOME) }
            ?: LoginState.Error("Unknown error")
    }
}

private fun LoginRequestUi.toLoginRequest() = LoginRequest(login, password)
private fun LoginResponse.toLoginResponseUi() = LoginResponseUi(userId)
