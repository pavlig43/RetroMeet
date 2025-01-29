package com.pavlig43.retromeetuicommon

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp

@Suppress("LongParameterList", "MagicNumber")
@Composable
fun LoginScreen(
    login: String,
    onLoginChanged: (String) -> Unit,
    password: String,
    onPasswordChanged: (String) -> Unit,
    tryEnter: () -> Unit,
    onRegisterScreen: () -> Unit,
    isLoading: Boolean,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        InputLoginParams(
            login = login,
            onLoginChanged = onLoginChanged,
            password = password,
            onPasswordChanged = onPasswordChanged,
            headerText = stringResource(R.string.enter)
        )
        LoadingButton(
            onClick = tryEnter,
            isLoading = isLoading,
            textButton = stringResource(R.string.enter),
            modifier = Modifier.fillMaxWidth(0.5f)
        )
        ButtonComponent(
            onClick = onRegisterScreen,
            textButton = stringResource(R.string.registration),
            modifier = Modifier.fillMaxWidth(0.5f)
        )
    }
}

@Suppress("LongParameterList")
@Composable
fun RegisterScreen(
    login: String,
    onLoginChanged: (String) -> Unit,
    password: String,
    onPasswordChanged: (String) -> Unit,
    onRegister: () -> Unit,
    isLoading: Boolean,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        InputLoginParams(
            login = login,
            onLoginChanged = onLoginChanged,
            password = password,
            onPasswordChanged = onPasswordChanged,
            headerText = stringResource(R.string.enter)
        )

        LoadingButton(
            onClick = onRegister,
            isLoading = isLoading,
            textButton = stringResource(R.string.registration),
        )
    }
}

@Suppress("LongParameterList")
@Composable
private fun InputLoginParams(
    login: String,
    onLoginChanged: (String) -> Unit,
    password: String,
    onPasswordChanged: (String) -> Unit,
    headerText: String,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {}
    HeadingTextComponent(headerText)
    TextFieldComponent(
        value = login,
        onValueChanged = onLoginChanged,
        labelValue = stringResource(R.string.login),
    )
    TextFieldComponent(
        value = password,
        onValueChanged = onPasswordChanged,
        labelValue = stringResource(R.string.password),
    )
}

@Composable
private fun HeadingTextComponent(value: String) {
    Text(
        text = value,
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(),
        style = TextStyle(
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold,
            fontStyle = FontStyle.Normal
        ),
        color = MaterialTheme.colorScheme.onBackground,
        textAlign = TextAlign.Center
    )
}

@Composable
private fun TextFieldComponent(
    value: String,
    onValueChanged: (String) -> Unit,
    labelValue: String
) {
    OutlinedTextField(
        label = {
            Text(text = labelValue)
        },
        value = value,
        onValueChange = onValueChanged,
        colors = TextFieldDefaults.colors().copy(
            focusedLabelColor = MaterialTheme.colorScheme.inversePrimary,
            cursorColor = MaterialTheme.colorScheme.primary,
            unfocusedContainerColor = MaterialTheme.colorScheme.background,
//            focusedTextColor = MaterialTheme.colorScheme.inversePrimary,

        ),

        modifier = Modifier.fillMaxWidth(),
        shape = MaterialTheme.shapes.medium,

        keyboardOptions = KeyboardOptions.Default
    )
}

@Composable
private fun ButtonComponent(
    onClick: () -> Unit,
    textButton: String,
    modifier: Modifier = Modifier
) {
    Button(onClick, modifier = modifier) {
        Text(text = textButton)
    }
}

@Suppress("UnusedPrivateMember")
@Preview(showBackground = true)
@Composable
private fun LoginPreview() {
    LoginScreen(
        login = "dadad",
        password = "afafaff",
        onLoginChanged = {},
        onPasswordChanged = {},
        tryEnter = {},
        onRegisterScreen = {},
        isLoading = false

    )
}
