package com.elitec.appmakeup.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.elitec.appmakeup.domain.project.ProjectLocation
import com.elitec.appmakeup.presentation.cache.RecentProjectsCache
import com.elitec.appmakeup.presentation.components.WindowsToolBar
import com.elitec.appmakeup.presentation.navigation.AppDestination
import com.elitec.appmakeup.presentation.screens.expanded.ModelingScreen
import com.elitec.appmakeup.presentation.screens.expanded.SplashScreen
import com.elitec.appmakeup.presentation.screens.expanded.WelcomeScreen
import com.elitec.appmakeup.presentation.theme.AppMakeupTheme
import com.elitec.appmakeup.presentation.uiStates.AppUiState
import com.elitec.appmakeup.presentation.util.ModelingMode
import com.elitec.appmakeup.presentation.util.toModelingMode

@Composable
fun AppRoot(
    modifier: Modifier = Modifier,
    isWindowsPlatform: Boolean = true,
    maximize: () -> Unit = {},
    minimize: () -> Unit = {},
    exit: () -> Unit = {}
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
    val route = navController

    AppMakeupTheme(darkTheme = appUiState.isDarkTheme) {
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            topBar = {
                if(isWindowsPlatform) {
                    WindowsToolBar(
                        maximize = maximize,
                        minimize = minimize,
                        exit = exit,
                        appTheme = appUiState,
                        onThemeChange = {
                            appUiState = appUiState.copy(
                                isDarkTheme = !appUiState.isDarkTheme
                            )
                        },
                        modifier = Modifier.fillMaxWidth().padding(
                            start = 10.dp
                        )
                    )
                }
            },
            bottomBar = {
                Text(
                    text = route.toString()
                )
            }
        ) { paddingValues ->
            NavHost(
                modifier = Modifier.fillMaxSize().padding(
                    paddingValues
                ),
                navController = navController,
                startDestination = AppDestination.Splash
            ) {
                composable<AppDestination.Splash> {
                    SplashScreen(
                        onDataReady = {
                            navController.navigate(AppDestination.Welcome)
                        }
                    )
                }
                composable<AppDestination.Welcome> {
                    WelcomeScreen(
                        isDarkTheme = appUiState.isDarkTheme,
                        onToggleTheme = {
                            appUiState = appUiState.copy(
                                isDarkTheme = !appUiState.isDarkTheme
                            )
                        },
                        onNavigateToModeling = { location ->
                            navController.navigate(
                                AppDestination.Modeling(location.value, ModelingMode.OPEN.name)
                            )
                        },
                        isWindowsApp = isWindowsPlatform,
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
}