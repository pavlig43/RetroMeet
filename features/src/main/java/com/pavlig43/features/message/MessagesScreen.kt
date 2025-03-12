package com.pavlig43.features.message

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.Send
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.pavlig43.features.R
import com.pavlig43.features.common.UserInfoItem
import com.pavlig43.features.common.onlinestatus.OnlineStatus
import com.pavlig43.features.message.model.MessageResponseUi
import com.pavlig43.features.message.model.MessageUserInfoUi
import com.pavlig43.retromeetuicommon.Photo

@Composable
fun MessagesScreen(
    viewModel: MessagesViewModel = hiltViewModel(),
    modifier: Modifier = Modifier
) {

    var isOpenMessageActionDialog by remember { mutableStateOf(false) }
    val messages: LazyPagingItems<MessageResponseUi> = viewModel.messages.collectAsLazyPagingItems()
    val messageRequest by viewModel.messageRequest.collectAsState()


    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        MessagesHeader(userInfo)
        val lazyState = rememberLazyListState()
        LaunchedEffect(messages.itemCount) {
            val snapshot = messages.itemSnapshotList
            val lastMessage = snapshot.firstOrNull()
            lastMessage?.let {
                if (it.userIsSender){
                    lazyState.animateScrollToItem(0)
                }
            }

        }

        Box {
            Messages(
                messages = messages,
                viewModel::retrySendMessage,
                state = lazyState,
                Modifier
                    .navigationBarsPadding()
                    .padding(vertical = 8.dp)
            )
            MessageTextField(
                value = messageRequest.text,
                onValueChange = viewModel::onValueChangeText,
                sendMessage = viewModel::sendMessage,
                maxChars = 500,
                Modifier
                    .align(Alignment.BottomCenter)
                    .padding(vertical = 8.dp)
            )
        }



        if (isOpenMessageActionDialog) {
            MessageActionDialog(
                onDismissRequest = { isOpenMessageActionDialog = false },
                reply = {}
            )
        }
    }

}

@Composable
fun MessageTextField(
    value: String,
    onValueChange: (String) -> Unit,
    sendMessage: () -> Unit,
    maxChars: Int = Int.MAX_VALUE,
    modifier: Modifier = Modifier
) {

    Row(modifier = modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
        TextField(
            value = value,
            onValueChange = { str ->
                if (str.length <= maxChars) onValueChange(str)
            },
            shape = RoundedCornerShape(16.dp),
            colors = TextFieldDefaults.colors(
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            ),
            modifier = Modifier.weight(0.8f)
        )
        IconButton(sendMessage) {
            Icon(
                Icons.AutoMirrored.Rounded.Send,
                "",
                tint = MaterialTheme.colorScheme.inverseSurface,
                modifier = Modifier.fillMaxHeight()

            )
        }
    }

}

@Composable
private fun MessageActionDialog(
    onDismissRequest: () -> Unit,
    reply: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Dialog(onDismissRequest = { onDismissRequest() }) {
        Card(
            modifier = modifier
                .fillMaxWidth()
                .height(375.dp)
                .padding(16.dp),
            shape = RoundedCornerShape(16.dp),
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {

                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center,
                ) {
                    TextButton(
                        onClick = reply,
                        modifier = Modifier.padding(8.dp),
                    ) {
                        Text(stringResource(R.string.reply))
                    }
                }
            }
        }
    }
}

@Composable
private fun MessagesHeader(
    userInfo: MessageUserInfoUi,
    modifier: Modifier = Modifier
) {
    Row(
        modifier
            .fillMaxWidth()
            .padding(bottom = 8.dp)
            .background(MaterialTheme.colorScheme.secondaryContainer),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Photo(
            userInfo.mainPhotoPath, modifier
                .size(60.dp)
                .padding(5.dp)
                .clip(CircleShape)

        )
        Column(
            Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                userInfo.name, Modifier.padding(vertical = 8.dp),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                color = MaterialTheme.colorScheme.onSecondaryContainer
            )
            userInfo.lastVisit?.let {
                UserInfoItem(R.string.last_visit_online, it)
            } ?: OnlineStatus(
                stringResource(R.string.now_online),
                true,
                textStyle = MaterialTheme.typography.labelMedium
            )
        }

    }

}

private val userInfo = MessageUserInfoUi(
    "PAVLIGffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffff",
    null,
    null
)

@Preview(showBackground = true)
@Composable
private fun DialogScreenPreview() {
    MessagesScreen()


}