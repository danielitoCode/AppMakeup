package com.elitec.appmakeup.presentation.states

import com.elitec.appmakeup.core.v4.definition.CoreEntity
import com.elitec.appmakeup.projects.model.AppFeature
import com.elitec.appmakeup.projects.model.AppMakeupProject
import com.elitec.appmakeup.projects.model.AppProperty

data class ProjectEditorUiState(
    val project: AppMakeupProject? = null,
    val features: List<AppFeature> = emptyList(),
    val selectedFeature: AppFeature? = null,
    val properties: List<AppProperty> = emptyList(),
    val isLoading: Boolean = false,
    val isExporting: Boolean = false, // ✅ NUEVO
    val error: String? = null
)