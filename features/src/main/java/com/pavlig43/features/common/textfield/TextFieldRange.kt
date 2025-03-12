package com.pavlig43.features.common.textfield

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import com.pavlig43.features.R
import com.pavlig43.features.common.UserInfoItemRow

@Composable
fun TextFieldRange(
    @StringRes description: Int,
    valueFrom: String?,
    valueTo: String?,
    onValueChangeFrom: (String) -> Unit,
    onValueChangeTo: (String) -> Unit,
    maxCharsFrom: Int = 2,
    maxCharsTo: Int = 2,
    isValidValueChange: (String) -> Boolean = { true },
    keyboardType: KeyboardType = KeyboardType.Companion.Number,
    modifier: Modifier = Modifier
) {
    UserInfoItemRow(
        stringResource(description), modifier = modifier
    ) {
        Column () {


            TextFieldResumeItem(
                R.string.from,
                value = valueFrom,
                onValueChange = onValueChangeFrom,
                maxChars = maxCharsFrom,
                keyboardType = keyboardType,
                isValidValueChange = isValidValueChange,
            )

            TextFieldResumeItem(
                R.string.to,
                value = valueTo,
                onValueChange = onValueChangeTo,
                maxChars = maxCharsTo,
                keyboardType = keyboardType,
                isValidValueChange = isValidValueChange,
            )
        }


    }

}

@Preview(showBackground = true)
@Composable
private fun TextFieldRangePrev() {
    TextFieldRange(
        R.string.age,
        valueFrom = "10",
        valueTo = "10",
        onValueChangeFrom = {},
        onValueChangeTo = {},
        modifier = Modifier
    )

}



