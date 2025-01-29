package com.pavlig43.retromeet.navigation.route.resume

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.pavlig43.features.mainscreen.route.MainScreenRoute
import com.pavlig43.features.resume.ResumeScreen
import com.pavlig43.features.resume.route.ResumeRoute

fun NavGraphBuilder.resume(
    navController: NavHostController
){
    composable<ResumeRoute>{
        ResumeScreen({ loginId -> navController.navigate(MainScreenRoute(loginId)) })
    }
}



