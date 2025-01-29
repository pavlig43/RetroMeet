package com.pavlig43.features.common

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

@Composable
fun CustomTextField(
    value: String?,
    onValueChange:(String)-> Unit,
    maxChars: Int,
    isValidValueChange: (String) -> Boolean = { true },
    keyboardType: KeyboardType = KeyboardType.Companion.Text,
    placeholder: String = "",
    modifier: Modifier = Modifier) {
    TextField(
        value = value ?: "",
        onValueChange = { newValue: String ->
            if (newValue.isBlank()) {
                onValueChange("")
            } else if (newValue.length <= maxChars && isValidValueChange(newValue)) {
                onValueChange(newValue)
            }

        },
        placeholder = {
            Text(
                placeholder,
                color = MaterialTheme.colorScheme.error
            )
        },
        textStyle = TextStyle.Companion.Default.copy(
            fontSize = FONT_SIZE.sp,
            textAlign = TextAlign.Companion.Center
        ),
        keyboardOptions = KeyboardOptions.Companion.Default.copy(keyboardType = keyboardType),
        modifier = modifier
            .wrapContentHeight()

    )

}
private const val FONT_SIZE = 22
