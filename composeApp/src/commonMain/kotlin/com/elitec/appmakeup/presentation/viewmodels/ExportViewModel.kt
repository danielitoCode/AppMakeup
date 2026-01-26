package com.elitec.appmakeup.presentation.viewmodels

import androidx.lifecycle.ViewModel
import com.elitec.appmakeup.core.v4.contracts.GenerationContext
import com.elitec.appmakeup.core.v4.contracts.GenerationResult
import com.elitec.appmakeup.generation.GenerateCodeUseCase
import com.elitec.appmakeup.generation.PreviewGenerationPlanUseCase
import com.elitec.appmakeup.presentation.states.ExportUiState
import com.elitec.appmakeup.presentation.states.GenerationPreviewUiState
import com.elitec.appmakeup.projects.usecase.project.LoadProjectUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

class ExportViewModel(
    private val loadProjectUseCase: LoadProjectUseCase,
    private val generateCodeUseCase: GenerateCodeUseCase,
    private val previewGenerationPlanUseCase: PreviewGenerationPlanUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(ExportUiState())
    val uiState: StateFlow<ExportUiState> = _uiState

    private val _uiPreviewState = MutableStateFlow(GenerationPreviewUiState())
    val uiPreviewState: StateFlow<GenerationPreviewUiState> = _uiPreviewState

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
            generateCodeUseCase.execute(
                project = project,
                dryRun = _uiState.value.dryRun
            )

            _uiState.update {
                it.copy(
                    result =
                        if (_uiState.value.dryRun)
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

    fun previewPlan(context: GenerationContext) {
        val plan = previewGenerationPlanUseCase.execute(context)

        _uiPreviewState.update {
            it.copy(
                generateDomain = plan.generateDomain,
                generateData = plan.generateData,
                generateRepositories = plan.generateRepositories,
                generateMappers = plan.generateMappers
            )
        }
    }

    fun runPreview() {
        val project = _uiState.value.project ?: return

        val result = generateCodeUseCase.execute(
            project = project,
            dryRun = true
        )

        if (result is GenerationResult.Preview) {
            _uiPreviewState.update {
                it.copy(
                    previewFiles = result.files
                )
            }
        }
    }
}