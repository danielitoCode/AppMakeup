package com.elitec.appmakeup.presentation.navigation

import kotlinx.serialization.Serializable

@Serializable
sealed interface MainScreens {
    @Serializable object Splash: MainScreens
    @Serializable object CreateProject: MainScreens
    @Serializable object Home: MainScreens
    @Serializable data class ProjectEditor(val path: String): MainScreens
    @Serializable data class Export(val path: String): MainScreens
}