package com.elitec.appmakeup.presentation.states

import com.elitec.appmakeup.core.v4.definition.CoreEntity
import com.elitec.appmakeup.projects.model.AppEntity
import com.elitec.appmakeup.projects.model.AppFeature
import com.elitec.appmakeup.projects.model.AppMakeupProject
import com.elitec.appmakeup.projects.model.AppProperty
import com.elitec.appmakeup.projects.validations.ValidationIssue

data class ProjectEditorUiState(
    val isLoading: Boolean = false,
    val project: AppMakeupProject? = null,
    val features: List<AppFeature> = emptyList(),
    val selectedFeature: AppFeature? = null,
    val selectedEntity: AppEntity? = null,
    val validationErrors: List<String> = emptyList(),
    val validationIssues: List<ValidationIssue> = emptyList(),
    val canExport: Boolean = false,
    val error: String? = null
)