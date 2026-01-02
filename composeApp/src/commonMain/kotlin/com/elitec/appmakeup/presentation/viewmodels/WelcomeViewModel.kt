package com.elitec.appmakeup.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.elitec.appmakeup.domain.project.ProjectLocation
import com.elitec.appmakeup.domain.project.usecase.CreateProjectWithTemplateUseCase
import com.elitec.appmakeup.domain.template.ProjectTemplate
import com.elitec.appmakeup.domain.template.StructureGenerationResult
import com.elitec.appmakeup.domain.usecase.CreateProjectUseCase
import com.elitec.appmakeup.domain.usecase.ValidateExistingProjectUseCase
import com.elitec.appmakeup.domain.validation.ProjectValidationResult
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
    private val createProjectWithTemplate: CreateProjectWithTemplateUseCase,
    private val recentProjectsRepo: RecentProjectsRepository,
    private val validateExistingProject: ValidateExistingProjectUseCase,
    private val defaultTemplate: ProjectTemplate
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

            val result = createProjectWithTemplate.execute(
                project = project,
                location = location,
                template = defaultTemplate
            )

            when (result) {
                is StructureGenerationResult.Success -> {
                    recentProjectsRepo.recordCreated(location)

                    _state.update {
                        it.copy(
                            isCreatingProject = false,
                            recentProjects = recentProjectsRepo.load()
                        )
                    }

                    onSuccess(location)
                }

                is StructureGenerationResult.Failure -> {
                    _state.update {
                        it.copy(
                            isCreatingProject = false,
                            error = "Failed to create project structure"
                        )
                    }
                }
            }
        }
    }

    fun openExistingProject(
        location: ProjectLocation,
        onSuccess: (ProjectLocation) -> Unit
    ) {
        viewModelScope.launch {
            when (val result = validateExistingProject.execute(location)) {

                ProjectValidationResult.Valid -> {
                    val updated = recentProjectsRepo.recordOpened(location)
                    _state.update { it.copy(recentProjects = updated) }
                    onSuccess(location)
                }

                is ProjectValidationResult.Invalid.MigratableVersion -> {
                    _state.update {
                        it.copy(
                            error = """
                    This project was created with an older version.
                    Project version: ${result.projectVersion}
                    Supported version: ${result.supportedVersion}
                    
                    Migration will be available in a future version.
                """.trimIndent()
                        )
                    }
                }

                is ProjectValidationResult.Invalid.UnsupportedVersion -> {
                    _state.update {
                        it.copy(
                            error = """
                    This project was created with a newer version of AppMakeup.
                    
                    Project version: ${result.projectVersion}
                    Supported version: ${result.supportedVersion}
                    
                    Please update AppMakeup.
                """.trimIndent()
                        )
                    }
                }

                is ProjectValidationResult.Invalid -> {
                    val message = when (result) {
                        ProjectValidationResult.Invalid.DirectoryNotFound ->
                            "The selected directory does not exist."
                        ProjectValidationResult.Invalid.MissingProjectFile ->
                            "This folder is not an AppMakeup project."
                        ProjectValidationResult.Invalid.InvalidProjectFile ->
                            "The project file is corrupted."
                    }

                    val updated = recentProjectsRepo.remove(location)
                    _state.update {
                        it.copy(
                            recentProjects = updated,
                            error = message
                        )
                    }
                }
            }
        }
    }

    fun clearError() {
        _state.update { it.copy(error = null) }
    }
}