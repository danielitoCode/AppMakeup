package com.elitec.appmakeup.presentation.states

data class CreateProjectUiState(
    val name: String = "",
    val packageName: String = "",
    val path: String = "",
    val error: String? = null,
    val isCreated: Boolean = false
)