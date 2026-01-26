package com.elitec.appmakeup.presentation.states

data class GenerationPreviewUiState(
    val generateDomain: Boolean = false,
    val generateData: Boolean = false,
    val generateRepositories: Boolean = false,
    val generateMappers: Boolean = false,
    val previewFiles: List<String> = emptyList()
)