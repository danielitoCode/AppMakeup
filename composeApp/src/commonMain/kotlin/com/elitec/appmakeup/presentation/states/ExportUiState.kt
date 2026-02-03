package com.elitec.appmakeup.presentation.states

import com.elitec.appmakeup.core.v5.preview.GenerationPreviewResult
import com.elitec.appmakeup.presentation.model.FileTreeNode
import com.elitec.appmakeup.projects.model.AppMakeupProject

data class ExportUiState(
    val project: AppMakeupProject? = null,
    val dryRun: Boolean = true,
    val isRunning: Boolean = false,
    val error: String? = null,
    val previews: List<GenerationPreviewResult> = emptyList(),
    val previewFiles: List<String> = emptyList(),
    val previewTree: FileTreeNode.Directory? = null

)