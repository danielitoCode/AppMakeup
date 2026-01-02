package com.elitec.appmakeup.presentation.navigation

import kotlinx.serialization.Serializable

@Serializable
sealed interface AppDestination {
    @Serializable
    data object Splash: AppDestination

    @Serializable
    data object Welcome: AppDestination

    @Serializable
    data class Modeling(
        val workspacePath: String,
        val projectName: String,
        val mode: String
    ): AppDestination {
        init {
            require(workspacePath.isNotBlank()) {
                "Project workspacePath cannot be empty"
            }
            require(projectName.isNotBlank()) {
                "Project projectName cannot be empty"
            }
            require(mode.isNotBlank()) {
                "Project mode cannot be empty"
            }
        }
    }
}