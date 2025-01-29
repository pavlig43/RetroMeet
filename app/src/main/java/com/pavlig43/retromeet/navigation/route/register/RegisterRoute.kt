package com.pavlig43.retromeet.navigation.route.register

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.pavlig43.features.mainscreen.route.MainScreenRoute
import com.pavlig43.features.register.RegisterScreen
import kotlinx.serialization.Serializable

@Serializable
data object RegisterRoute

fun NavGraphBuilder.register(
    navController: NavHostController
) {
    composable<RegisterRoute> {
        RegisterScreen(
            onEnter = { login -> navController.navigate(MainScreenRoute(login)) }
        )
    }
}
