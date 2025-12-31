package com.elitec.appmakeup.presentation.uiStates

import com.elitec.appmakeup.domain.project.Project
import com.elitec.appmakeup.domain.project.ProjectLocation
import com.elitec.appmakeup.domain.validation.RuleViolation

data class ModelingState(
    val project: Project? = null,
    val location: ProjectLocation? = null,
    val selectedFeatureName: String? = null,
    val validationErrors: List<RuleViolation> = emptyList(),
    val isDirty: Boolean = false
)