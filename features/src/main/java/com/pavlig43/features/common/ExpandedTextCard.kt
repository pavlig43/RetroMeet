package com.pavlig43.features.common

import androidx.annotation.StringRes
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.LinkAnnotation
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withLink
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.pavlig43.features.R


@Composable
fun ExpandableTextCard(
    text: String,
    openMessageActionDialog: () -> Unit = {},
    modifier: Modifier = Modifier,
    cardColor: Color = Color.Unspecified,
    collapsedMaxLines: Int = 5,
    style: TextStyle = MaterialTheme.typography.bodyMedium,
    @StringRes showMoreRes: Int = R.string.next,
    @StringRes showLessRes: Int = R.string.collapse,
    showMoreStyle: SpanStyle = SpanStyle(Color.Blue, fontSize = 11.sp),
    showLessStyle: SpanStyle = SpanStyle(Color.Blue, fontSize = 11.sp)
) {

    val showMoreText = stringResource(showMoreRes)
    val showLessText = stringResource(showLessRes)
    var isExpanded by remember { mutableStateOf(false) }
    var isClickable by remember { mutableStateOf(false) }
    var lastCharInd by remember { mutableIntStateOf(0) }
    val annotatedText = buildAnnotatedString {
        if (isClickable) {
            if (isExpanded) {
                append(text)
                withLink(
                    link = LinkAnnotation.Clickable(
                        tag = "Show Less",
                        linkInteractionListener = { isExpanded = !isExpanded }
                    )
                ) {
                    withStyle(showLessStyle) {
                        append(showLessText)
                    }
                }

            } else {
                val addText = text.substring(0, lastCharInd)
                    .drop(showMoreText.length)
                    .dropLastWhile { it.isWhitespace() || it == '.' }
                append(addText)
                withLink(
                    link = LinkAnnotation.Clickable(
                        tag = "Show More",
                        linkInteractionListener = { isExpanded = !isExpanded }
                    )
                ) {
                    withStyle(showMoreStyle) {
                        append(showMoreText)
                    }
                }

            }

        } else {
            append(text)
        }


    }
    Card(
        colors = CardDefaults.cardColors(containerColor = cardColor),
        modifier = modifier
        .clip(RoundedCornerShape(15.dp))
        .pointerInput(Unit) {
            detectTapGestures(
                onDoubleTap = { isExpanded = !isExpanded },
                onLongPress = {openMessageActionDialog()}
            )
        }
    ) {
        Text(
            text = annotatedText,
            style = style,
            maxLines = if (isExpanded) Int.MAX_VALUE else collapsedMaxLines,
            onTextLayout = { textLayoutResult ->
                if (!isExpanded && textLayoutResult.hasVisualOverflow) {
                    isClickable = true
                    lastCharInd = textLayoutResult.getLineEnd(collapsedMaxLines - 1)
                }
            },
            modifier = Modifier.padding(4.dp)
        )

    }
}

@Preview(showBackground = true)
@Composable
private fun ExpandableTextPrev() {
    val text = List(50) { "Слово" }.joinToString()
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp)
            .verticalScroll(
                rememberScrollState()
            ),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        repeat(10) {
            ExpandableTextCard(text)
        }
    }
}