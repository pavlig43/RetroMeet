package com.pavlig43.features.userInfo

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.pavlig43.features.R
import com.pavlig43.features.common.enumui.model.FriendStatusUi
import com.pavlig43.features.common.onlinestatus.OnlineStatus
import com.pavlig43.features.userInfo.model.UserInfoUi
import com.pavlig43.features.utils.getCountYearsToCurrentYear
import com.pavlig43.retromeetuicommon.ErrorScreen
import com.pavlig43.retromeetuicommon.LoadingScreen

@Composable
fun UserInfoScreen(
    modifier: Modifier = Modifier,
    viewModel: UserInfoViewModel = hiltViewModel(),
) {
    val userInfoState by viewModel.userInfo.collectAsState()
    when (val state = userInfoState) {
        is UserInfoState.Error -> ErrorScreen(state.message, modifier)
        UserInfoState.Loading -> LoadingScreen(modifier)
        is UserInfoState.Success -> UserInfoScreen(
            userInfo = state.data,
            changeFriedStatus = viewModel::changeFriendStatus,
            modifier
        )
    }

}

@Composable
private fun UserInfoScreen(
    userInfo: UserInfoUi,
    changeFriedStatus: (FriendStatusUi) -> Unit,
    modifier: Modifier = Modifier
) {
    val scrollState = rememberScrollState()
    Column(
        modifier
            .fillMaxWidth()
            .verticalScroll(scrollState)
    ) {
        UserInfoResume(
            userInfo = userInfo,
            goToPhotoAlbum = {},
            changeFriedStatus = changeFriedStatus
        )
    }


}

@Composable
private fun UserInfoResume(
    userInfo: UserInfoUi,
    goToPhotoAlbum: (Int) -> Unit,
    changeFriedStatus: (FriendStatusUi) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier
            .fillMaxWidth()
    ) {

        TextButton({ goToPhotoAlbum(userInfo.resumeUi.userId) }) {
            Text(stringResource(R.string.photo_album), fontSize = 18.sp)
        }
        FriendComponent(
            userInfo.friendStatusUi,
            changeFriedStatus

        )
        UserInfoItem(R.string.id, userInfo.resumeUi.userId.toString())
        userInfo.lastVisit?.let {
            UserInfoItem(R.string.last_visit_online,it)
        }?: OnlineStatus(stringResource(R.string.now_online),true)

        UserInfoItem(R.string.date_registration, userInfo.resumeUi.registeredAt)

        userInfo.resumeUi.name.takeIf { it.isNotEmpty() }?.let {
            UserInfoItem(R.string.name, it)
        }
        userInfo.resumeUi.city.takeIf { it.isNotEmpty() }?.let {
            UserInfoItem(R.string.city, it)
        }
        userInfo.resumeUi.dateOfBirth?.let {
            UserInfoItem(R.string.age, it.getCountYearsToCurrentYear().toString())
        }
        userInfo.resumeUi.weight?.let {
            UserInfoItem(R.string.weight_kg, it)
        }
        userInfo.resumeUi.height?.let {
            UserInfoItem(R.string.height_cm, it)
        }
        listOf(
            userInfo.resumeUi.gender,
            userInfo.resumeUi.orientation,
            userInfo.resumeUi.eyeColor,
            userInfo.resumeUi.hairColor,
            userInfo.resumeUi.education,
            userInfo.resumeUi.smoking,
            userInfo.resumeUi.drinking,
            userInfo.resumeUi.maritalStatus,
            userInfo.resumeUi.religion,
        ).forEach { enum ->
            enum.takeIf { it.translate != R.string.not_important }?.let {
                UserInfoItem(it.description, stringResource(it.translate))
            }
        }
        userInfo.resumeUi.countChildren?.let {
            UserInfoItem(R.string.count_children, it)
        }
        userInfo.resumeUi.occupation?.let {
            UserInfoItem(R.string.occupation, it)
        }
        userInfo.resumeUi.hobbies?.let {
            UserInfoItem(R.string.hobbies, it)
        }
        userInfo.resumeUi.interests?.let {
            UserInfoItem(R.string.interests, it)
        }
        userInfo.resumeUi.aboutMe?.let {
            UserInfoItem(R.string.about_me, it)
        }
        userInfo.resumeUi.favoriteMovies?.let {
            UserInfoItem(R.string.favorite_movies, it)
        }
        userInfo.resumeUi.favoriteBooks?.let {
            UserInfoItem(R.string.favorite_books, it)
        }
        listOf(userInfo.resumeUi.pets, userInfo.resumeUi.favoriteMusicGenres).forEach { lst ->
            lst.takeIf { it.isNotEmpty() }?.let { items ->
                UserInfoItem(
                    items.first().description,
                    items.map { item -> stringResource(item.translate) }.joinToString(",")
                )
            }

        }


    }
}

@Composable
private fun FriendComponent(
    friendStatusUi: FriendStatusUi,
    changeFriedStatus: (FriendStatusUi) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier.fillMaxWidth()) {
        TextButton({ changeFriedStatus(friendStatusUi) }) {
            Text(stringResource(friendStatusUi.action), fontSize = 18.sp)
        }

        if (friendStatusUi == FriendStatusUi.REQUEST_MINUS){
            TextButton({ changeFriedStatus(FriendStatusUi.REQUEST_PLUS) }) {
                Text(stringResource(FriendStatusUi.REQUEST_PLUS.action), fontSize = 18.sp)
            }
        }
    }


}

@Composable
private fun UserInfoItem(
    @StringRes description: Int,
    info: String,
    modifier: Modifier = Modifier
) {
    val descriptionText = stringResource(description)
    Text("$descriptionText: $info", modifier = modifier)

}

