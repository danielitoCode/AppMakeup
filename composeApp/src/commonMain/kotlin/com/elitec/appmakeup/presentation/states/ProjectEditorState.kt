package com.elitec.appmakeup.presentation.states

import com.elitec.appmakeup.projects.model.AppFeature
import com.elitec.appmakeup.projects.model.AppMakeupProject

data class ProjectEditorState(
    val project: AppMakeupProject,
    val selectedFeature: AppFeature? = null,
    val isExporting: Boolean = false,
    val error: String? = null
)