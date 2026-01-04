package com.elitec.appmakeup.presentation.uiStates

import com.elitec.appmakeup.domain.model.Project
import com.elitec.appmakeup.domain.model.ProjectLocation

sealed class AppState {

    data object Welcome : AppState()

    data class Modeling(
        val project: Project,
        val location: ProjectLocation
    ) : AppState()
}