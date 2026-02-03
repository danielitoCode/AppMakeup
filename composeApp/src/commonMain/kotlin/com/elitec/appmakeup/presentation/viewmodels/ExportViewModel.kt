package com.elitec.appmakeup.presentation.viewmodels

import androidx.lifecycle.ViewModel
import com.elitec.appmakeup.core.v4.contracts.GenerationResult
import com.elitec.appmakeup.core.v5.domain.contracts.GenerationContext
import com.elitec.appmakeup.generation.GenerateCodeUseCase
import com.elitec.appmakeup.generation.PreviewGenerationPlanUseCase
import com.elitec.appmakeup.logs.Logger
import com.elitec.appmakeup.presentation.mapper.FileTreeBuilder
import com.elitec.appmakeup.presentation.model.FileTreeNode
import com.elitec.appmakeup.presentation.states.ExportUiState
import com.elitec.appmakeup.presentation.states.GenerationPreviewUiState
import com.elitec.appmakeup.presentation.util.buildFileTree
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

    private val tag = "[ExportViewModel] --->"
    private val _uiState = MutableStateFlow(ExportUiState())
    val uiState: StateFlow<ExportUiState> = _uiState

    private val _fileTree = MutableStateFlow<FileTreeNode.Directory?>(null)
    val fileTree: StateFlow<FileTreeNode.Directory?> = _fileTree

    fun loadProject(path: String) {
        val project = loadProjectUseCase.execute(path)
        _uiState.update { it.copy(project = project) }
    }

    fun toggleDryRun(enabled: Boolean) {
        _uiState.update { it.copy(dryRun = enabled) }
    }

    fun runPreview() {

        FileTreeBuilder.build(
            listOf(
                "features/maestro/domain/entities/Maestro.kt",
                "features/maestro/data/repository/MaestroRepository.kt"
            )
        )

        val project = _uiState.value.project ?: return

        val paths = generateCodeUseCase.preview(project)

        paths.forEach {
            Logger.success(tag,"TREE INPUT: $it" )
        }

        _fileTree.value =
            FileTreeBuilder.build(paths)
    }

    fun runExport() {
        val project = _uiState.value.project ?: return

        generateCodeUseCase.execute(
            project = project,
            dryRun = _uiState.value.dryRun
        )
    }
}