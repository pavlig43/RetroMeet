package com.pavlig43.features.common.textfield

import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import com.pavlig43.features.R
import com.pavlig43.features.common.CustomTextField
import com.pavlig43.features.common.UserInfoItemRow

@Composable
 fun TextFieldResumeItem(
    @StringRes description: Int,
    value: String?,
    onValueChange: (String) -> Unit,
    maxChars: Int = 30,
    isValidValueChange: (String) -> Boolean = { true },
    keyboardType: KeyboardType = KeyboardType.Companion.Text,

    modifier: Modifier = Modifier.Companion
) {
    UserInfoItemRow(text = stringResource(description), modifier = modifier) {

        CustomTextField(
            value = value,
            onValueChange = onValueChange,
            maxChars = maxChars,
            isValidValueChange = isValidValueChange,
            keyboardType = keyboardType,
            placeholder = stringResource(R.string.not_important)

        )

    }
}
