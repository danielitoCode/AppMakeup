package com.elitec.appmakeup.presentation.navigation

import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.elitec.appmakeup.presentation.screens.CreateProjectScreen
import com.elitec.appmakeup.presentation.screens.ExportScreen
import com.elitec.appmakeup.presentation.screens.HomeScreen
import com.elitec.appmakeup.presentation.screens.ProjectEditorScreen
import com.elitec.appmakeup.presentation.screens.SplashScreen
import com.elitec.appmakeup.presentation.theme.onBackgroundDark

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun MainNavigationWrapper(
    onAppReady: () -> Unit,
    modifier: Modifier = Modifier
) {
    val navController = rememberNavController()
    SharedTransitionScope {
        NavHost(
            navController = navController,
            startDestination = MainScreens.Splash,
            modifier = modifier.fillMaxSize()
        ) {
            composable<MainScreens.Splash> {
                SplashScreen(
                    sharedTransitionScope = this@SharedTransitionScope,
                    navigateTo = { destination ->
                        onAppReady()
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
                    sharedTransitionScope = this@SharedTransitionScope,
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
                    onNavigateToExport = {
                        navController.navigate(MainScreens.Export(path))
                    }
                )
            }
            composable<MainScreens.Export> { backStackEntry ->
                val path = backStackEntry.toRoute<MainScreens.Export>().path
                ExportScreen(
                    projectPath = path,
                    onBack = { navController.popBackStack() }
                )
            }
        }
    }
}