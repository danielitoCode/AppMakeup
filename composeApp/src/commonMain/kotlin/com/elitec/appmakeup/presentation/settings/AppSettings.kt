package com.elitec.appmakeup.presentation.settings

import kotlinx.serialization.Serializable

@Serializable
data class AppSettings(
    val recentProjects: List<String> = emptyList()
)