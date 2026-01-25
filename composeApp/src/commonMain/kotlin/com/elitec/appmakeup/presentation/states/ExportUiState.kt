package com.elitec.appmakeup.presentation.states

import com.elitec.appmakeup.projects.model.AppMakeupProject

data class ExportUiState(
    val project: AppMakeupProject? = null,
    val dryRun: Boolean = true,
    val isRunning: Boolean = false,
    val result: String? = null,
    val error: String? = null
)