package com.pavlig43.features.mainscreen

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.pavlig43.features.R
import com.pavlig43.features.mainscreen.model.MainScreenPreviewUi
import com.pavlig43.retromeetuicommon.ErrorScreen
import com.pavlig43.retromeetuicommon.LoadingScreen
import com.pavlig43.retromeetuicommon.Photo

@Suppress("LongParameterList")
@Composable
fun MainScreen(
    onExit: () -> Unit,
    onResumeScreen: (Int) -> Unit,
    onFriendsScreen: () -> Unit,
    onMessagesScreen: () -> Unit,
    onSearchScreen: () -> Unit,
    viewModel: MainScreenViewModel = hiltViewModel(),
    modifier: Modifier = Modifier
) {
    val previewState by viewModel.userInfoPreviewState.collectAsState()
    val requestToFriend by viewModel.requestToFriend.collectAsState()
    val unreadMessages by viewModel.unreadMessages.collectAsState()
    when (val state = previewState) {
        is UserInfoPreviewState.Error -> ErrorScreen(state.errorMessage, modifier)
        is UserInfoPreviewState.Loading -> LoadingScreen(modifier)
        is UserInfoPreviewState.Success -> MainScreenPrivate(
            onExit = onExit,
            onResumeScreen = { viewModel.goToScreen { onResumeScreen(it) } },
            onFriendsScreen = onFriendsScreen,
            onMessagesScreen = onMessagesScreen,
            onSearchScreen = onSearchScreen,
            mainScreenPreviewUi = state.data,
            requestToFriend = requestToFriend,
            unreadMessages = unreadMessages,
            modifier = modifier
        )
    }
}

@Suppress("LongParameterList")
@Composable
private fun MainScreenPrivate(
    onExit: () -> Unit,
    onResumeScreen: () -> Unit = {},
    onFriendsScreen: () -> Unit = {},
    onMessagesScreen: () -> Unit = {},
    onSearchScreen: () -> Unit = {},
    mainScreenPreviewUi: MainScreenPreviewUi,
    requestToFriend: Int = 0,
    unreadMessages: Int = 0,
    modifier: Modifier = Modifier
) {
    val verticalState = rememberScrollState()
    Column(modifier = modifier.verticalScroll(verticalState)) {
        PreviewPhotoAndInfo(onExit, mainScreenPreviewUi.name, mainScreenPreviewUi.mainPhotoPath)
        MenuItemRow(
            icon = R.drawable.resumeicon,
            text = R.string.resume,
            goToScreen = onResumeScreen,
            contentDescription = stringResource(R.string.resume_form)
        )
        MenuItemRow(
            icon = R.drawable.friendsicon,
            text = R.string.friends,
            goToScreen = onFriendsScreen,
            someThingValue = requestToFriend.takeIf { it != 0 }?.toString() ?: "",
            contentDescription = stringResource(R.string.friends_description)
        )
        MenuItemRow(
            icon = R.drawable.messageicon,
            text = R.string.messages,
            goToScreen = onMessagesScreen,
            someThingValue = unreadMessages.takeIf { it != 0 }?.toString() ?: "",
            contentDescription = stringResource(R.string.messages_description)
        )
        MenuItemRow(
            icon = R.drawable.searchicon,
            text = R.string.search,
            goToScreen = onSearchScreen,
            contentDescription = stringResource(R.string.search_description)
        )
    }
}

@Composable
private fun PreviewPhotoAndInfo(
    onExit: () -> Unit,
    name: String?,
    mainPhotoPath: String?,
    modifier: Modifier = Modifier
) {
    val height = 200.dp
    val widthPhoto = 150.dp
    Row(
        modifier = modifier
            .height(height)
            .fillMaxWidth()
    ) {
        Photo(mainPhotoPath, modifier = Modifier.size(widthPhoto, height))
        Column(
            modifier = Modifier
                .fillMaxHeight()
                .padding(horizontal = 4.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = name ?: "", style = MaterialTheme.typography.headlineLarge)
            BuildString(
                text = R.string.views_per_week,
                clickableText = "100",
                onTextClick = { /*TODO*/ }
            )
            Spacer(modifier = Modifier.weight(1f))
            BuildString(
                clickableText = stringResource(R.string.exit),
                onTextClick = onExit,
                clickableTextSize = 28.sp,
                modifier = Modifier.align(Alignment.End)
            )
        }
    }
}

@Suppress("LongParameterList")
@Composable
private fun BuildString(
    clickableText: String,
    clickableTextSize: TextUnit = 18.sp,
    @StringRes text: Int? = null,
    textSize: TextUnit = 18.sp,
    onTextClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val text = buildAnnotatedString {
        text?.let {
            withStyle(SpanStyle(fontSize = textSize)) {
                append(stringResource(text))
            }
        }
        pushStringAnnotation("click", annotation = "click")
        withStyle(
            SpanStyle(
                textDecoration = TextDecoration.Underline,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                fontSize = clickableTextSize
            )
        ) {
            append(clickableText)
        }
    }
    Text(
        text = text,
        modifier = modifier
            .clickable {
                text.getStringAnnotations("click", start = 0, end = text.length)
                    .firstOrNull()?.let {
                        onTextClick()
                    }
            },
    )
}

@Suppress("LongParameterList")
@Composable
private fun MenuItemRow(
    goToScreen: () -> Unit = {},
    @DrawableRes icon: Int,
    @StringRes text: Int,
    contentDescription: String? = null,
    someThingValue: String? = null,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(64.dp)
            .border(1.dp, color = MaterialTheme.colorScheme.onPrimaryContainer)
            .clickable { goToScreen() },
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Icon(
            painter = painterResource(icon),
            contentDescription = contentDescription,
            tint = Color.Unspecified,
            modifier = Modifier
                .size(48.dp)
                .padding(horizontal = 8.dp)
        )
        Text(text = stringResource(text), style = MaterialTheme.typography.headlineMedium)
        someThingValue?.let {
            Spacer(Modifier.weight(1f))
            Text(
                text = it,
                modifier.padding(end = 8.dp),
                style = MaterialTheme.typography.headlineMedium,
                textDecoration = TextDecoration.Underline,
                color = MaterialTheme.colorScheme.error
            )
        }
    }
}

@Suppress("UnusedPrivateMember")
@Preview(showBackground = true)
@Composable
private fun MainScreenPreview() {
    MainScreenPrivate(
        onExit = {},
        mainScreenPreviewUi = MainScreenPreviewUi(loginId = 1, name = "name", age = 35)
    )
}
