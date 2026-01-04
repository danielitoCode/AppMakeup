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
        val mode: String
    ) : AppDestination
}