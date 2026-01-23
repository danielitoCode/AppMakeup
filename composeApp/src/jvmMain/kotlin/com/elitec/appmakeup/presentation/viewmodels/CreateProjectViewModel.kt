package com.elitec.appmakeup.presentation.viewmodels

import androidx.lifecycle.ViewModel
import com.elitec.appmakeup.presentation.states.CreateProjectUiState
import com.elitec.appmakeup.projects.model.AppMakeupProject
import com.elitec.appmakeup.projects.usecase.project.CreateProjectUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

class CreateProjectViewModel(
    private val createProjectUseCase: CreateProjectUseCase
): ViewModel() {
    private val _uiState = MutableStateFlow(CreateProjectUiState())
    val uiState: StateFlow<CreateProjectUiState> = _uiState

    fun onNameChange(value: String) =
        _uiState.update { it.copy(name = value) }

    fun onPackageChange(value: String) =
        _uiState.update { it.copy(packageName = value) }

    fun onPathChange(value: String) =
        _uiState.update { it.copy(path = value) }

    fun createProject() {
        val state = _uiState.value

        if (state.name.isBlank() ||
            state.packageName.isBlank() ||
            state.path.isBlank()
        ) {
            _uiState.update {
                it.copy(error = "Todos los campos son obligatorios")
            }
            return
        }

        val project = AppMakeupProject(
            name = state.name,
            packageName = state.packageName,
            path = state.path,
            features = emptyList()
        )

        createProjectUseCase.execute(project)

        _uiState.update {
            it.copy(isCreated = true, error = null)
        }
    }
}