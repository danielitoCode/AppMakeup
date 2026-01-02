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
        val path: String,
        val mode: String
    ): AppDestination {
        init {
            require(path.isNotBlank()) {
                "Project location cannot be empty"
            }
            require(mode.isNotBlank()) {
                "Project mode cannot be empty"
            }
        }
    }
}