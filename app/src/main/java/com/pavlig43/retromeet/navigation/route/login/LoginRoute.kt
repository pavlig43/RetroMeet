package com.pavlig43.retromeet.navigation.route.login

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.pavlig43.features.login.LoginScreen
import com.pavlig43.features.login.route.LoginRoute
import com.pavlig43.features.mainscreen.route.MainScreenRoute
import com.pavlig43.retromeet.navigation.route.register.RegisterRoute

fun NavGraphBuilder.login(
    navController: NavHostController
) {
    composable<LoginRoute> {
        LoginScreen(
            onEnter = { loginId -> navController.navigate(MainScreenRoute(loginId)) },
            onRegisterScreen = { navController.navigate(RegisterRoute) }
        )
    }
}
