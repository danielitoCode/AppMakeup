package com.elitec.appmakeup.presentation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.elitec.appmakeup.domain.project.ProjectLocation
import com.elitec.appmakeup.presentation.cache.RecentProjectsCache
import com.elitec.appmakeup.presentation.navigation.AppDestination
import com.elitec.appmakeup.presentation.screens.expanded.ModelingScreen
import com.elitec.appmakeup.presentation.screens.expanded.WelcomeScreen
import com.elitec.appmakeup.presentation.theme.AppMakeupTheme
import com.elitec.appmakeup.presentation.uiStates.AppUiState
import com.elitec.appmakeup.presentation.util.ModelingMode
import com.elitec.appmakeup.presentation.util.toModelingMode

@Composable
fun AppRoot(
    modifier: Modifier = Modifier
) {
    var appUiState by remember {
        mutableStateOf(
            AppUiState(
                isDarkTheme = true,
                recentProjects = RecentProjectsCache.get()
            )
        )
    }

    val navController = rememberNavController()

    AppMakeupTheme(darkTheme = appUiState.isDarkTheme) {

        NavHost(
            navController = navController,
            startDestination = AppDestination.Welcome
        ) {
            composable<AppDestination.Welcome> {
                WelcomeScreen(
                    isDarkTheme = appUiState.isDarkTheme,
                    recentProjects = appUiState.recentProjects,

                    onToggleTheme = {
                        appUiState = appUiState.copy(
                            isDarkTheme = !appUiState.isDarkTheme
                        )
                    },

                    onCreateProject = { project, location ->
                        RecentProjectsCache.add(location)

                        appUiState = appUiState.copy(
                            recentProjects = RecentProjectsCache.get()
                        )

                        navController.navigate(AppDestination.Modeling(
                            location.value,
                            ModelingMode.CREATE.name
                            ))
                    },
                    onNavigateToModeling = { location ->
                        navController.navigate(
                            AppDestination.Modeling(location.value, ModelingMode.OPEN.name)
                        )
                    },
                    onOpenProject = { location ->
                        RecentProjectsCache.add(location)

                        appUiState = appUiState.copy(
                            recentProjects = RecentProjectsCache.get()
                        )

                        navController.navigate(
                            AppDestination.Modeling(
                                location.value,
                                ModelingMode.CREATE.name
                            )
                        )
                    }
                )
            }
            composable<AppDestination.Modeling> { backStackEntry ->
                val path = backStackEntry.toRoute<AppDestination.Modeling>().path
                val mode = backStackEntry.toRoute<AppDestination.Modeling>().mode
                val location = ProjectLocation(path)
                ModelingScreen(
                    projectLocation = location,
                    mode = mode.toModelingMode(),
                    onBack = { navController.popBackStack() }
                )
            }
        }
    }
}