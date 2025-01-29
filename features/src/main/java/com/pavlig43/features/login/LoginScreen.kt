package com.pavlig43.features.login

import android.content.Context
import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import com.pavlig43.retromeetuicommon.LoginScreen

@Composable
fun LoginScreen(
    onEnter: (Int) -> Unit,
    onRegisterScreen: () -> Unit,
    viewModel: LoginViewModel = hiltViewModel(),
    modifier: Modifier = Modifier
) {
    val login by viewModel.login.collectAsState()
    val loginState by viewModel.loginState.collectAsState()
    val buttonState by viewModel.buttonState.collectAsState()
    val context = LocalContext.current
    LoginScreen(
        login = login.login,
        onLoginChanged = viewModel::onLoginChanged,
        password = login.password,
        onPasswordChanged = viewModel::onPasswordChanged,
        tryEnter = {
            viewModel.tryEnter { showToast(context, it) }
        },
        onRegisterScreen = onRegisterScreen,
        isLoading = buttonState,
        modifier = modifier,
    )

    when (val state = loginState) {
        is LoginState.Error -> {}
        LoginState.Loading -> {}
        is LoginState.Success -> { onEnter(state.loginId) }
        LoginState.Start -> {}
    }
}

private fun showToast(context: Context, message: String?) {
    message?.let {
        Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
    }
}
