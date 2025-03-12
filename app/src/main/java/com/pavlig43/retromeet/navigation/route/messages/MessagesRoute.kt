package com.pavlig43.retromeet.navigation.route.messages

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.pavlig43.features.message.MessagesScreen
import com.pavlig43.features.message.route.MessagesRoute

fun NavGraphBuilder.messages(
    navController: NavHostController
) {
    composable<MessagesRoute> {
        MessagesScreen()
    }
}
