package com.elitec.appmakeup.presentation.navigation

import kotlinx.serialization.Serializable

@Serializable
sealed interface MainScreens {
    sealed interface Splash: MainScreens
    sealed interface CreateProject: MainScreens
    sealed interface ProjectEditor: MainScreens
    sealed interface Export: MainScreens
}