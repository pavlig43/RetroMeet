package com.pavlig43.features.common

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
internal fun UserInfoItem(
    text: String,
    modifier: Modifier = Modifier,
    item: @Composable () -> Unit,

    ) {
    Card(modifier = modifier) {
        Row(
            Modifier
                .fillMaxWidth()
                .heightIn(min = 60.dp),
            verticalAlignment = Alignment.Companion.CenterVertically
        ) {
            Text(
                text,
                fontSize = FONT_SIZE.sp,
                modifier = Modifier
                    .fillMaxWidth(WIDTH_COLUMN)
                    .padding(start = 8.dp)
            )
            item()

        }
    }

}
private const val FONT_SIZE = 22
private const val WIDTH_COLUMN = 0.4f
