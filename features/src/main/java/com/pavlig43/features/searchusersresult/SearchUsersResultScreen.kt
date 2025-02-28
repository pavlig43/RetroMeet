package com.pavlig43.features.searchusersresult

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.pavlig43.features.R
import com.pavlig43.features.common.enumui.model.ShortGenderUi
import com.pavlig43.features.common.items
import com.pavlig43.features.common.onlinestatus.OnlineStatus
import com.pavlig43.features.searchusersresult.model.SearchUserPreviewUi
import com.pavlig43.retromeetuicommon.Photo

@Composable
fun SearchUsersResultScreen(
    onUserScreen: (Int) -> Unit,
    viewModel: SearchUserResultViewModel = hiltViewModel(),
    modifier: Modifier = Modifier
) {
    val result = viewModel.result.collectAsLazyPagingItems()
    SearchUsersResultScreenInternal(onUserScreen, result, modifier)


}

@Composable
internal fun SearchUsersResultScreenInternal(
    onUserScreen: (Int) -> Unit,
    searchResult: LazyPagingItems<SearchUserPreviewUi>,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(searchResult) { user ->
            UserCardPreview(user, onUserScreen, )
        }
    }

}


@Composable
private fun UserCardPreview(
    userPreview: SearchUserPreviewUi,
    onUserScreen: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    val height = 200.dp
    val widthPhoto = 150.dp
    Card(
        onClick = { onUserScreen(userPreview.userId) },
        modifier = modifier
            .height(height)
            .fillMaxWidth()
    ) {
        Row(Modifier.fillMaxWidth()) {
            Photo(userPreview.mainPhotoPath, modifier = Modifier.size(widthPhoto, height))
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 4.dp),
                horizontalAlignment = Alignment.CenterHorizontally,

                ) {
                OnlineStatus(userPreview.name,userPreview.isOnline)
                val ageValue = userPreview.age?.toString() ?: "_"
                val ageText = stringResource(R.string.age)
                val displayAgeText = "$ageText: $ageValue"
                Text(
                    displayAgeText,
                    style = MaterialTheme.typography.headlineLarge
                )
                val genderValue = userPreview.gender?.translate?.let { stringResource(it) } ?: "_"
                val genderText = stringResource(R.string.gender)
                val displayGenderText = "$genderText: $genderValue"
                Text(
                    displayGenderText,
                    style = MaterialTheme.typography.headlineLarge
                )
                val cityText = stringResource(R.string.city)
                val cityValue = userPreview.city ?: "_"
                val displayCityText = "$cityText: $cityValue"
                Text(
                    displayCityText,
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }

    }

}

@Preview
@Composable
private fun UserCardPrev() {
    UserCardPreview(
        userPreview = SearchUserPreviewUi(
            userId = 1,
            gender = ShortGenderUi.Male,
            mainPhotoPath = null,
            name = "LJLJ",
            age = 35,
            isOnline = false
        ),
        onUserScreen = {},

    )

}


