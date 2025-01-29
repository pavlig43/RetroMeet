package com.pavlig43.features.common.textfield

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.Placeholder
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import com.pavlig43.features.R
import com.pavlig43.features.common.CustomTextField
import com.pavlig43.features.common.UserInfoItem

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
    UserInfoItem(text = stringResource(description), modifier = modifier) {

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
