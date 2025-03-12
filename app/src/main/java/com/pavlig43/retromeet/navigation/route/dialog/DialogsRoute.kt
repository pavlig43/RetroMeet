package com.pavlig43.retromeet.navigation.route.dialog

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.pavlig43.features.dialog.DialogsScreen
import com.pavlig43.features.dialog.route.DialogsRoute
import com.pavlig43.features.message.route.MessagesRoute

fun NavGraphBuilder.dialogs(
    navController: NavHostController
) {
    composable<DialogsRoute> {
        DialogsScreen(onMessagesScreen = { navController.navigate(MessagesRoute(it)) })
    }
}
