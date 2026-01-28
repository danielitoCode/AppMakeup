package com.elitec.appmakeup.presentation.viewmodels

import androidx.lifecycle.ViewModel
import com.elitec.appmakeup.core.v4.contracts.GenerationResult
import com.elitec.appmakeup.core.v5.domain.contracts.GenerationContext
import com.elitec.appmakeup.generation.GenerateCodeUseCase
import com.elitec.appmakeup.generation.PreviewGenerationPlanUseCase
import com.elitec.appmakeup.presentation.states.ExportUiState
import com.elitec.appmakeup.presentation.states.GenerationPreviewUiState
import com.elitec.appmakeup.projects.usecase.preview.PreviewGenerationUseCase
import com.elitec.appmakeup.projects.usecase.project.LoadProjectUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

class ExportViewModel(
    private val loadProjectUseCase: LoadProjectUseCase,
    private val generateCodeUseCase: GenerateCodeUseCase,
    private val previewGenerationUseCase: PreviewGenerationUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(ExportUiState())
    val uiState: StateFlow<ExportUiState> = _uiState

    fun loadProject(path: String) {
        val project = loadProjectUseCase.execute(path)
        _uiState.update { it.copy(project = project) }
    }

    fun toggleDryRun(enabled: Boolean) {
        _uiState.update { it.copy(dryRun = enabled) }
    }

    fun runPreview() {
        val project = _uiState.value.project ?: return

        val previews = previewGenerationUseCase.execute(project)
        _uiState.update { it.copy(previews = previews) }
    }

    fun runExport() {
        val project = _uiState.value.project ?: return

        generateCodeUseCase.execute(
            project = project,
            dryRun = _uiState.value.dryRun
        )
    }
}