package com.pavlig43.retromeet.navigation.route.userinfo

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.pavlig43.features.message.route.MessagesRoute
import com.pavlig43.features.userInfo.UserInfoScreen
import com.pavlig43.features.userInfo.route.UserInfoRoute

fun NavGraphBuilder.userInfo(
    navController: NavHostController
) {
    composable<UserInfoRoute> {
        UserInfoScreen(writeMessage = { navController.navigate(MessagesRoute(it)) })
    }
}
