package com.pavlig43.features.message

import androidx.compose.animation.core.InfiniteRepeatableSpec
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Replay
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.paging.compose.LazyPagingItems
import com.pavlig43.features.R
import com.pavlig43.features.common.ExpandableTextCard
import com.pavlig43.features.common.items
import com.pavlig43.features.message.model.MessageStatusUi
import com.pavlig43.features.message.model.MessageResponseUi


@OptIn(ExperimentalLayoutApi::class)
@Composable
internal fun Messages(
    messages: LazyPagingItems<MessageResponseUi>,
    retrySendMessage: (Long) -> Unit,
    state:LazyListState,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        reverseLayout = true,
        state = state,
        modifier = modifier,
        contentPadding = PaddingValues(bottom = 32.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {

        items(messages, key = { it.localId }) { message ->
            MessageRow(message, retrySendMessage)
        }



    }

}

@Composable
private fun MessageRow(
    message: MessageResponseUi,
    retrySendMessage: (Long) -> Unit,
    modifier: Modifier = Modifier.fillMaxWidth()
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = if (message.userIsSender) Arrangement.End else Arrangement.Start
    ) {
        if (message.status == MessageStatusUi.ERROR) {
            RetrySendIcon({ retrySendMessage(message.localId) }, Modifier.fillMaxWidth(0.2f))
        }

        MessageBody(message, Modifier.fillMaxWidth(0.8f))
    }
}

@Composable
private fun RetrySendIcon(
    retrySendMessage: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        IconButton(retrySendMessage) {
            Icon(
                Icons.Default.Replay,
                stringResource(R.string.retry_send_message),
                Modifier.size(48.dp)
            )
        }
    }
}

@Composable
private fun MessageBody(
    message: MessageResponseUi,
    modifier: Modifier = Modifier
) {
    Column(modifier) {
        val cardColor =
            if (message.userIsSender) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.secondary
        ExpandableTextCard(
            text = message.toString(),
            cardColor = cardColor,
            modifier = Modifier

        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            if (message.userIsSender) {
                val color = when (message.status) {
                    MessageStatusUi.READ -> Color.Blue
                    MessageStatusUi.LOAD -> Color.Gray
                    MessageStatusUi.ERROR -> MaterialTheme.colorScheme.error
                    MessageStatusUi.IN_PROCESS -> MaterialTheme.colorScheme.onPrimaryContainer
                }
                message.status.let {
                    val iconModifier = if (it.isAnimate){
                        val infiniteTransition = rememberInfiniteTransition()
                        val rotation by infiniteTransition.animateFloat(
                            initialValue = 0f,
                            targetValue = 360f,
                            animationSpec = InfiniteRepeatableSpec(
                                animation = tween(1000, easing = LinearEasing),
                                repeatMode = RepeatMode.Restart
                            )
                        )
                        Modifier.rotate(rotation)
                    }
                    else{Modifier}
                    Icon(
                        imageVector = it.icon,
                        contentDescription = stringResource(it.contentDescription),
                        tint = color,
                        modifier = iconModifier
                    )
                }
            }
            Text(message.time, fontSize = 11.sp)
        }
    }

}


private const val CARD_WIDTH = 0.8f

@Preview(showBackground = true)
@Composable
private fun MessageRowPrev() {
    val message = MessageResponseUi(
        localId = 100L,
        serverId = 100L,
        sender = 100,
        recipient = 100,
        userIsSender = false,
        replyMessageId = null,
        text = "FES",
        time = "88888888888",
        status = MessageStatusUi.ERROR,
        attachment = null
    )
    MessageRow(
        message = message, {}
    )

}