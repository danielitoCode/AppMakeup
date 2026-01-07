package com.elitec.appmakeup.presentation.uiStates

import com.elitec.appmakeup.domain.codegen.CodeTree
import com.elitec.appmakeup.domain.model.Project
import com.elitec.appmakeup.domain.model.ProjectLocation

data class ModelingState(
    val project: Project? = null,
    val selectedFeatureName: String? = null,
    val codeTree: CodeTree? = null,
    val validationErrors: List<String> = emptyList(),
    val isDirty: Boolean = false
)