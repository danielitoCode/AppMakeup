package com.elitec.appmakeup.presentation.uiStates

import com.elitec.appmakeup.domain.project.ProjectLocation

data class AppUiState(
    val isDarkTheme: Boolean = true,
    val recentProjects: List<ProjectLocation> = emptyList(),
    val lastOpenedProject: ProjectLocation? = null,
    val uiScale: Float = 1.0f,
    val showWelcomeOnStartup: Boolean = true
)