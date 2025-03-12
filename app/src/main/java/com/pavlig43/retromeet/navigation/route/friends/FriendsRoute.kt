package com.pavlig43.retromeet.navigation.route.friends

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.pavlig43.features.friends.FriendsScreen
import com.pavlig43.features.friends.route.FriendsRoute
import com.pavlig43.features.mainscreen.route.MainScreenRoute
import com.pavlig43.features.userInfo.route.UserInfoRoute

fun NavGraphBuilder.friends(
    navController: NavHostController
) {
    composable<FriendsRoute> {
        FriendsScreen(
            onUserScreen = { navController.navigate(UserInfoRoute(it)) },
            onBack = {
                navController.popBackStack()
                navController.navigate(MainScreenRoute)
            }

        )
    }
}
