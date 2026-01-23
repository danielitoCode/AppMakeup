package com.elitec.appmakeup.presentation.viewmodels

import androidx.lifecycle.ViewModel
import com.elitec.appmakeup.presentation.states.HomeUiState
import com.elitec.appmakeup.projects.usecase.project.ListRecentProjectsUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

class HomeViewModel(
    private val listRecentProjectsUseCase: ListRecentProjectsUseCase
): ViewModel() {

    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState

    fun loadRecentProjects() {
        try {
            val recent = listRecentProjectsUseCase.execute()
            _uiState.update { it.copy(recentProjects = recent) }
        } catch (e: Exception) {
            _uiState.update { it.copy(error = e.message) }
        }
    }
}