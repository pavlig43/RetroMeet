package com.pavlig43.retromeetuicommon

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun LoadingButton(
    onClick: () -> Unit,
    textButton: String,
    isLoading: Boolean,
    modifier: Modifier = Modifier
) {
    Button(
        onClick,
        modifier = modifier
    ) {
        if (isLoading) {
            CircularProgressIndicator(
                color = MaterialTheme.colorScheme.onPrimary,
                strokeWidth = 2.dp,
                modifier = Modifier
                    .size(24.dp)
                    .padding(4.dp)
            )
        }
        Text(text = textButton)
    }
}
