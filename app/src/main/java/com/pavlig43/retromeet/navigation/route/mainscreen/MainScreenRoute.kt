package com.pavlig43.retromeet.navigation.route.mainscreen

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.pavlig43.features.dialog.route.DialogsRoute
import com.pavlig43.features.friends.route.FriendsRoute
import com.pavlig43.features.login.route.LoginRoute
import com.pavlig43.features.mainscreen.MainScreen
import com.pavlig43.features.mainscreen.route.MainScreenRoute
import com.pavlig43.features.searchuser.route.SearchUserRoute
import com.pavlig43.features.userInfo.nested.resume.route.ResumeRoute

fun NavGraphBuilder.mainScreen(
    navController: NavHostController
) {
    composable<MainScreenRoute> {
        MainScreen(
            onExit = { navController.navigate(LoginRoute) { popUpTo(0) { inclusive = true } } },
            onResumeScreen = { navController.navigate(ResumeRoute(it)) },
            onFriendsScreen = { navController.navigate(FriendsRoute) },
            onMessagesScreen = { navController.navigate(DialogsRoute) },
            onSearchScreen = { navController.navigate(SearchUserRoute) }
        )
    }
}
