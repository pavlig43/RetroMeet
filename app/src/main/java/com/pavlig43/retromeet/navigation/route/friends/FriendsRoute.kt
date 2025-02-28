package com.pavlig43.retromeet.navigation.route.friends

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.pavlig43.features.friendscreen.FriendsScreen
import com.pavlig43.features.friendscreen.route.FriendsRoute
import com.pavlig43.features.userInfo.route.UserInfoRoute

fun NavGraphBuilder.friends(
    navController: NavHostController
) {
    composable<FriendsRoute> {
        FriendsScreen({navController.navigate(UserInfoRoute(it))})
    }
}
