package com.elitec.appmakeup.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.elitec.appmakeup.domain.model.Project
import com.elitec.appmakeup.domain.model.ProjectLocation
import com.elitec.appmakeup.domain.model.ProjectSession
import com.elitec.appmakeup.domain.recent.RecentProjectsStore
import com.elitec.appmakeup.domain.usecase.CreateProjectUseCase
import com.elitec.appmakeup.domain.usecase.LoadProjectUseCase
import com.elitec.appmakeup.presentation.uiStates.ModelingState
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
    private val loadProject: LoadProjectUseCase,
    private val recentProjectsStore: RecentProjectsStore
) : ViewModel() {

    private val _state = MutableStateFlow(WelcomeState())
    val state: StateFlow<WelcomeState> = _state

    init {
        _state.update {
            it.copy(recentProjects = recentProjectsStore.load())
        }
    }

    fun createNewProject(
        config: CreateProjectConfig,
        onSuccess: (ProjectLocation, String) -> Unit
    ) = viewModelScope.launch {

        _state.update { it.copy(isCreatingProject = true, error = null) }

        val project = createProjectFromConfig(config)
        val workspace = ProjectLocation(config.workspacePath)

        createProject.execute(
            location = workspace,
            project = project
        )

        val projectRoot = ProjectLocation(
            (workspace.value.toPath() / project.name).toString()
        )

        val updated = (_state.value.recentProjects + projectRoot)
            .distinct()
            .take(10)

        recentProjectsStore.save(updated)

        _state.update {
            it.copy(
                isCreatingProject = false,
                recentProjects = updated
            )
        }

        onSuccess(workspace, project.name)
    }

    fun openRecentProject(
        location: ProjectLocation,
        onSuccess: (Project) -> Unit
    ) = viewModelScope.launch {

        val project = loadProject.execute(location)

        if (project == null) {
            _state.update {
                it.copy(error = "Project not found or project file is missing.")
            }
            return@launch
        }

        val updated = (listOf(location) + _state.value.recentProjects)
            .distinct()
            .take(10)

        recentProjectsStore.save(updated)

        _state.update {
            it.copy(
                recentProjects = updated,
                error = null
            )
        }

        onSuccess(project)
    }

    fun clearError() {
        _state.update { it.copy(error = null) }
    }
}
