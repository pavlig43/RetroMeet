package com.pavlig43.features.dialog

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.pavlig43.features.R
import com.pavlig43.features.common.items
import com.pavlig43.features.common.onlinestatus.OnlineStatus
import com.pavlig43.features.dialog.model.DialogResponseUi
import com.pavlig43.features.dialog.model.DialogUserPreviewUi
import com.pavlig43.retromeetuicommon.Photo

@Composable
fun DialogsScreen(
    onMessagesScreen: (Int) -> Unit,
    viewModel: DialogsViewModel = hiltViewModel(),
    modifier: Modifier = Modifier
) {
    val dialogs: LazyPagingItems<DialogResponseUi> = viewModel.dialogs.collectAsLazyPagingItems()
    DialogList(dialogs,onMessagesScreen, modifier)

}

@Composable
private fun DialogList(
    dialogs: LazyPagingItems<DialogResponseUi>,
    onMessagesScreen: (Int) -> Unit,
    modifier: Modifier = Modifier
) {

    val state = rememberLazyListState()
    LazyColumn(
        state = state,
        modifier = modifier,
        contentPadding = PaddingValues(bottom = 32.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {

        items(dialogs, key = { it.localId }) { dialog ->
            DialogUserCardPreview(
                dialog = dialog,
                onMessagesScreen = onMessagesScreen
            )
        }

    }

}

@Composable
private fun DialogUserCardPreview(
    dialog: DialogResponseUi,
    onMessagesScreen: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        onClick = { onMessagesScreen(dialog.info.friendId) },
        modifier = modifier
            .height(100.dp)
            .fillMaxWidth()
    ) {
        Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
            Photo(
                dialog.userInfo.mainPhoto, modifier
                    .size(90.dp)
                    .padding(5.dp)
                    .clip(CircleShape)

            )
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 4.dp),
                horizontalAlignment = Alignment.CenterHorizontally,

                ) {
                OnlineStatus(dialog.userInfo.name, dialog.userInfo.isOnline)

            }
        }

    }

}

