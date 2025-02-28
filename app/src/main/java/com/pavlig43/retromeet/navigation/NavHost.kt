package com.pavlig43.retromeet.navigation

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.pavlig43.features.login.route.LoginRoute
import com.pavlig43.features.online.OnlineViewModel
import com.pavlig43.features.searchuser.route.SearchUserRoute
import com.pavlig43.features.searchusersresult.route.SearchUserResultRoute
import com.pavlig43.retromeet.navigation.route.friends.friends
import com.pavlig43.retromeet.navigation.route.login.login
import com.pavlig43.retromeet.navigation.route.mainscreen.mainScreen
import com.pavlig43.retromeet.navigation.route.register.register
import com.pavlig43.retromeet.navigation.route.resume.resume
import com.pavlig43.retromeet.navigation.route.searchUsersResult.searchUsersResult
import com.pavlig43.retromeet.navigation.route.searchusers.searchUsers
import com.pavlig43.retromeet.navigation.route.userinfo.userInfo
import com.pavlig43.retromeetdata.onlineRepository.OnlineRepository

@Composable
private fun NavigationHost(
    navController: NavHostController,
) {
    NavHost(navController, startDestination = LoginRoute) {
        login(navController)
        register(navController)
        mainScreen(navController)
        resume(navController)
        searchUsers(navController)
        searchUsersResult(navController)
        friends(navController)
        userInfo(navController)
    }
}

@Composable
fun NavigationHost(
    onlineViewModel: OnlineViewModel = hiltViewModel(),
    modifier: Modifier = Modifier) {
    val online by onlineViewModel.online.collectAsState(null)
    val navController = rememberNavController()
    Column(modifier = modifier.fillMaxWidth().padding(horizontal = 8.dp)) {
        NavigationHost(navController = navController)
    }
}
