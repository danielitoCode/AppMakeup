package com.elitec.appmakeup.presentation.uiStates

import com.elitec.appmakeup.domain.project.Project
import com.elitec.appmakeup.domain.project.ProjectLocation

sealed class AppState {

    data object Welcome : AppState()

    data class Modeling(
        val project: Project,
        val location: ProjectLocation
    ) : AppState()
}