package com.elitec.appmakeup.presentation.states

data class HomeUiState(
    val recentProjects: List<String> = emptyList(),
    val error: String? = null
)