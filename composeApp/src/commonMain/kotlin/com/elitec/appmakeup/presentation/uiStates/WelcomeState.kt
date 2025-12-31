package com.elitec.appmakeup.presentation.uiStates

import com.elitec.appmakeup.domain.project.ProjectLocation

data class WelcomeState(
    val isDarkTheme: Boolean = true,
    val recentProjects: List<ProjectLocation> = emptyList(),
    val isCreatingProject: Boolean = false,
    val error: String? = null
)