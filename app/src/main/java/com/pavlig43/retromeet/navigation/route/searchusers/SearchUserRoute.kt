package com.pavlig43.retromeet.navigation.route.searchusers

import androidx.compose.foundation.layout.Box
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.pavlig43.features.login.LoginScreen
import com.pavlig43.features.login.route.LoginRoute
import com.pavlig43.features.mainscreen.route.MainScreenRoute
import com.pavlig43.features.searchuser.SearchUsersScreen
import com.pavlig43.features.searchuser.model.DateOfBirthRangeUi
import com.pavlig43.features.searchuser.model.HeightRangeUi
import com.pavlig43.features.searchuser.model.SearchUserRequestUi
import com.pavlig43.features.searchuser.model.WeightRangeUi
import com.pavlig43.features.searchuser.route.SearchUserRoute
import com.pavlig43.retromeet.navigation.route.register.RegisterRoute
import com.pavlig43.retromeet.navigation.route.searchUsersResult.typeMapOf

fun NavGraphBuilder.searchUsers(
    navController: NavHostController
) {
    composable<SearchUserRoute>(

    ) {
        SearchUsersScreen({filter-> navController.navigate(filter)})
    }
}
