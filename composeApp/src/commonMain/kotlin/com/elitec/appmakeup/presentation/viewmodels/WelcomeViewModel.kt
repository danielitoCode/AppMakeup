package com.elitec.appmakeup.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.elitec.appmakeup.domain.model.Project
import com.elitec.appmakeup.domain.model.ProjectLocation
import com.elitec.appmakeup.domain.usecase.CreateProjectUseCase
import com.elitec.appmakeup.domain.usecase.LoadProjectUseCase
import com.elitec.appmakeup.presentation.uiStates.WelcomeState
import com.elitec.appmakeup.presentation.util.CreateProjectConfig
import com.elitec.appmakeup.presentation.util.createProjectFromConfig
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import okio.Path.Companion.toPath

class WelcomeViewModel(
    private val createProject: CreateProjectUseCase,
    private val loadProject: LoadProjectUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(WelcomeState())
    val state: StateFlow<WelcomeState> = _state

    fun createNewProject(
        config: CreateProjectConfig,
        onSuccess: (ProjectLocation, String) -> Unit
    ) = viewModelScope.launch {

        _state.update { it.copy(isCreatingProject = true, error = null) }

        // 1️⃣ Crear Project en memoria
        val project: Project = createProjectFromConfig(config)

        // 2️⃣ Workspace
        val workspace = ProjectLocation(config.workspacePath)

        // 3️⃣ Persistir proyecto (se crea /<project.name>)
        createProject.execute(
            project = project
        )

        // 4️⃣ Project root real
        val projectRoot = ProjectLocation(
            (workspace.value.toPath() / project.name).toString()
        )

        // 5️⃣ Actualizar recientes
        _state.update {
            it.copy(
                isCreatingProject = false,
                recentProjects = it.recentProjects + projectRoot
            )
        }

        // 6️⃣ Navegar
        onSuccess(workspace, project.name)
    }

    fun openRecentProject(
        location: ProjectLocation,
        onSuccess: (Project) -> Unit
    ) = viewModelScope.launch {

        // Load project from the recent project root
        val project = loadProject.execute(location)

        if (project == null) {
            _state.update {
                it.copy(error = "Project not found or project file is missing.")
            }
            return@launch
        }

        // Move to top
        _state.update {
            it.copy(
                recentProjects = (listOf(location) + it.recentProjects).distinct().take(10),
                error = null
            )
        }

        onSuccess(project)
    }

    fun clearError() {
        _state.update { it.copy(error = null) }
    }
}
