package com.pavlig43.retromeet.navigation.route.searchusers

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.pavlig43.features.searchuser.SearchUsersScreen
import com.pavlig43.features.searchuser.route.SearchUserRoute
import com.pavlig43.features.searchusersresult.route.SearchUserResultRoute

fun NavGraphBuilder.searchUsers(
    navController: NavHostController
) {
    composable<SearchUserRoute> {
        SearchUsersScreen({ navController.navigate(SearchUserResultRoute) })
    }
}
