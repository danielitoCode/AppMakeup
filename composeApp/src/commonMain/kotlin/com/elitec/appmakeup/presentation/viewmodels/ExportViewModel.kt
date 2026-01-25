package com.elitec.appmakeup.presentation.viewmodels

import androidx.lifecycle.ViewModel
import com.elitec.appmakeup.generation.GenerateCodeUseCase
import com.elitec.appmakeup.presentation.states.ExportUiState
import com.elitec.appmakeup.projects.usecase.project.LoadProjectUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

class ExportViewModel(
    private val loadProjectUseCase: LoadProjectUseCase,
    private val generateCodeUseCase: GenerateCodeUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(ExportUiState())
    val uiState: StateFlow<ExportUiState> = _uiState

    /* ---------------------------
     * Init
     * --------------------------- */

    fun loadProject(path: String) {
        try {
            val project = loadProjectUseCase.execute(path)
            _uiState.update {
                it.copy(project = project)
            }
        } catch (e: Exception) {
            _uiState.update {
                it.copy(error = e.message)
            }
        }
    }

    /* ---------------------------
     * Options
     * --------------------------- */

    fun toggleDryRun(enabled: Boolean) {
        _uiState.update { it.copy(dryRun = enabled) }
    }

    /* ---------------------------
     * Export
     * --------------------------- */

    fun runExport() {
        val project = _uiState.value.project ?: return

        _uiState.update {
            it.copy(isRunning = true, error = null, result = null)
        }

        try {
            // 🔑 El dry-run debe venir reflejado en el proyecto / contexto
            generateCodeUseCase.execute(
                project, true
            )

            _uiState.update {
                it.copy(
                    result = if (_uiState.value.dryRun)
                        "Dry-run completed successfully"
                    else
                        "Project exported successfully"
                )
            }
        } catch (e: Exception) {
            _uiState.update {
                it.copy(error = e.message)
            }
        } finally {
            _uiState.update {
                it.copy(isRunning = false)
            }
        }
    }
}