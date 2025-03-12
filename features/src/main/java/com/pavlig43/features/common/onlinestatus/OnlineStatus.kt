package com.pavlig43.features.common.onlinestatus

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.focusGroup
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun OnlineStatus(
    name: String?,
    isOnline: Boolean,
    textStyle: TextStyle = MaterialTheme.typography.headlineLarge,
    modifier: Modifier = Modifier) {
    Row(modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Canvas(Modifier.padding(horizontal = 12.dp)) {
            drawCircle(
                color = if (isOnline) Color.Green else Color.Red,
                radius = 20f
            )
        }
        Text(text = name ?: "", style = textStyle, color = MaterialTheme.colorScheme.onSecondaryContainer)
    }
}

@Preview(showBackground = true)
@Composable
private fun OnlineStatusPreview() {
    Box {
        OnlineStatus(name = "name", isOnline = true)
    }


}