package com.pavlig43.features.register

import android.content.Context
import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import com.pavlig43.retromeetuicommon.RegisterScreen

@Composable
fun RegisterScreen(
    onEnter: () -> Unit,
    viewModel: RegisterViewModel = hiltViewModel(),
    modifier: Modifier = Modifier
) {
    val registerUi by viewModel.registerUi.collectAsState()
    val registerState by viewModel.registerState.collectAsState()
    val buttonState by viewModel.buttonState.collectAsState()
    val context = LocalContext.current
    RegisterScreen(
        login = registerUi.login,
        onLoginChanged = viewModel::onLoginChanged,
        password = registerUi.password,
        onPasswordChanged = viewModel::onPasswordChanged,
        onRegister = {
            viewModel.tryRegister { message -> showToast(context, message) }
        },
        isLoading = buttonState,
        modifier = modifier,
    )

    when (val state = registerState) {
        is RegisterState.Error -> {}
        RegisterState.Loading -> {}
        is RegisterState.Success -> { onEnter() }
        RegisterState.Start -> {}
    }
}
private fun showToast(context: Context, message: String?) {
    message?.let {
        Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
    }
}
