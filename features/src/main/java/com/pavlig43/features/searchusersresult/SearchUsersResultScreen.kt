package com.pavlig43.features.searchusersresult

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.pavlig43.features.searchuser.model.SearchUserRequestUi

@Composable
fun SearchUsersResultScreen(
    viewModel: SearchUserResultViewModel = hiltViewModel(),
    modifier: Modifier = Modifier
) {
    val route by viewModel.route.collectAsState()
    SearchUsersResultScreenPrivate(route, modifier)
    Column {
        viewModel.map.forEach {
            Text(it.toString())
        }
    }


}

@Composable
private fun SearchUsersResultScreenPrivate(
    route: SearchUserRequestUi,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier.fillMaxWidth()) {
//        Text(route.toString())
    }

}
