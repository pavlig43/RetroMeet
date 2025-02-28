package com.pavlig43.retromeet.navigation.route.searchUsersResult

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.pavlig43.features.searchusersresult.SearchUsersResultScreen
import com.pavlig43.features.searchusersresult.route.SearchUserResultRoute
import com.pavlig43.features.userInfo.route.UserInfoRoute

fun NavGraphBuilder.searchUsersResult(
    navController: NavHostController
) {
    composable<SearchUserResultRoute> {
        SearchUsersResultScreen({navController.navigate(UserInfoRoute(it))})
    }
}
