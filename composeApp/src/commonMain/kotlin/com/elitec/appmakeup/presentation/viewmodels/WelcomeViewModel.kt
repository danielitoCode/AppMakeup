package com.elitec.appmakeup.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.elitec.appmakeup.domain.project.ProjectLocation
import com.elitec.appmakeup.domain.usecase.CreateProjectUseCase
import com.elitec.appmakeup.presentation.cache.RecentProjectsCache
import com.elitec.appmakeup.presentation.settings.RecentProjectsRepository
import com.elitec.appmakeup.presentation.uiStates.WelcomeState
import com.elitec.appmakeup.presentation.util.CreateProjectConfig
import com.elitec.appmakeup.presentation.util.createProjectFromConfig
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class WelcomeViewModel(
    private val createProject: CreateProjectUseCase,
    private val recentProjectsRepo: RecentProjectsRepository
) : ViewModel() {

    private val _state = MutableStateFlow(WelcomeState())
    val state: StateFlow<WelcomeState> = _state

    init {
        loadRecentProjects()
        println("Loaded recent projects: ${recentProjectsRepo.load()}")
    }

    private fun loadRecentProjects() {
        val projects = recentProjectsRepo.load()
        _state.update {
            it.copy(recentProjects = projects)
        }
    }

    fun toggleTheme() {
        _state.update { it.copy(isDarkTheme = !it.isDarkTheme) }
    }

    fun createNewProject(
        config: CreateProjectConfig,
        onSuccess: (ProjectLocation) -> Unit
    ) {
        viewModelScope.launch {
            _state.update { it.copy(isCreatingProject = true) }

            val location = config.toProjectLocation()
            val project = createProjectFromConfig(config)

            createProject.execute(location, project)

            val updated = recentProjectsRepo.recordCreated(location)

            _state.update {
                it.copy(
                    isCreatingProject = false,
                    recentProjects = updated
                )
            }

            onSuccess(location)
        }
    }

    fun openExistingProject(
        location: ProjectLocation,
        onSuccess: (ProjectLocation) -> Unit
    ) {
        val updated = recentProjectsRepo.recordOpened(location)

        _state.update {
            it.copy(recentProjects = updated)
        }

        onSuccess(location)
    }

    fun clearError() {
        _state.update { it.copy(error = null) }
    }
}