package com.pavlig43.retromeet.navigation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.pavlig43.features.login.LoginScreen
import com.pavlig43.features.login.route.LoginRoute
import com.pavlig43.features.resume.route.ResumeRoute
import com.pavlig43.features.searchuser.route.SearchUserRoute
import com.pavlig43.retromeet.navigation.route.login.login
import com.pavlig43.retromeet.navigation.route.mainscreen.mainScreen
import com.pavlig43.retromeet.navigation.route.register.register
import com.pavlig43.retromeet.navigation.route.resume.resume
import com.pavlig43.retromeet.navigation.route.searchUsersResult.searchUsersResult
import com.pavlig43.retromeet.navigation.route.searchusers.searchUsers

@Composable
private fun NavigationHost(
    navController: NavHostController,
) {
    NavHost(navController, startDestination = SearchUserRoute) {
        login(navController)
        register(navController)
        mainScreen(navController)
        resume(navController)
        searchUsers(navController)
        searchUsersResult(navController)
    }
}

@Composable
fun NavigationHost(modifier: Modifier = Modifier) {
    val navController = rememberNavController()
    Column(modifier = modifier.fillMaxWidth().padding(horizontal = 8.dp)) {
        NavigationHost(navController = navController)
    }
}
