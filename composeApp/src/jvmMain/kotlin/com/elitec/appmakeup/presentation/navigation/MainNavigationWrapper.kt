package com.elitec.appmakeup.presentation.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.elitec.appmakeup.presentation.screens.CreateProjectScreen
import com.elitec.appmakeup.presentation.screens.HomeScreen
import com.elitec.appmakeup.presentation.screens.ProjectEditorScreen
import com.elitec.appmakeup.presentation.screens.SplashScreen

@Composable
fun MainNavigationWrapper(
    modifier: Modifier = Modifier
) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = MainScreens.Splash,
        modifier = modifier.fillMaxSize()
    ) {
        composable<MainScreens.Splash> {
            SplashScreen(
                navigateTo = { destination ->
                    navController.navigate(MainScreens.Home) {
                        popUpTo(MainScreens.Splash) { inclusive = true }
                    }
                }
            )
        }
        composable<MainScreens.CreateProject> {
            CreateProjectScreen(
                onProjectCreate = { path ->
                    navController.navigate(MainScreens.ProjectEditor(path)) {
                        popUpTo(MainScreens.CreateProject) { inclusive = true }
                    }
                }
            )
        }
        composable<MainScreens.Home> {
            HomeScreen(
                onProjectCreate = {
                    navController.navigate(MainScreens.CreateProject)
                },
                onOpenProject = { path ->
                    navController.navigate(MainScreens.ProjectEditor(path))
                }
            )
        }
        composable<MainScreens.ProjectEditor> { backStackEntry ->
            val path = backStackEntry.toRoute<MainScreens.ProjectEditor>().path

            ProjectEditorScreen(
                projectPath = path,
                onExport = {
                    navController.navigate(MainScreens.Export(path))
                }
            )
        }
    }
}