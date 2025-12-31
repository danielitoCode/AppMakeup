package com.elitec.appmakeup.presentation.viewmodels

import androidx.lifecycle.ViewModel
import com.elitec.appmakeup.domain.project.ProjectLocation
import com.elitec.appmakeup.domain.usecase.CreateProjectUseCase
import com.elitec.appmakeup.presentation.cache.RecentProjectsCache
import com.elitec.appmakeup.presentation.uiStates.WelcomeState
import com.elitec.appmakeup.presentation.util.CreateProjectConfig
import com.elitec.appmakeup.presentation.util.createProjectFromConfig
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

class WelcomeViewModel(
    private val createProject: CreateProjectUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(WelcomeState(recentProjects = RecentProjectsCache.get()))
    val state: StateFlow<WelcomeState> = _state

    // -------------------------
    // UI State
    // -------------------------

    fun toggleTheme() {
        _state.update {
            it.copy(isDarkTheme = !it.isDarkTheme)
        }
    }

    // -------------------------
    // Project creation
    // -------------------------

    fun createNewProject(
        config: CreateProjectConfig,
        onSuccess: (ProjectLocation) -> Unit
    ) {
        try {
            val location = config.toProjectLocation()
            val project = createProjectFromConfig(config)

            // 🔴 AQUÍ se materializa el proyecto
            createProject.execute(location, project)

            RecentProjectsCache.add(location)

            _state.update {
                it.copy(
                    recentProjects = RecentProjectsCache.get(),
                    error = null
                )
            }

            onSuccess(location)

        } catch (e: Exception) {
            _state.update {
                it.copy(error = e.message)
            }
        }
    }

    // -------------------------
    // Open existing
    // -------------------------

    fun openExistingProject(
        location: ProjectLocation,
        onSuccess: (ProjectLocation) -> Unit
    ) {
        RecentProjectsCache.add(location)

        _state.update {
            it.copy(
                recentProjects = RecentProjectsCache.get(),
                error = null
            )
        }

        onSuccess(location)
    }

    fun clearError() {
        _state.update { it.copy(error = null) }
    }
}