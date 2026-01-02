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
import okio.Path.Companion.toPath

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
        onSuccess: (ProjectLocation, String) -> Unit
    ) {
        viewModelScope.launch {

            _state.update { it.copy(isCreatingProject = true) }

            // 1️⃣ Workspace
            val workspace = config.toProjectLocation()

            // 2️⃣ Nombre del proyecto
            val projectName = config.appName.trim()

            // 3️⃣ Proyecto de dominio
            val project = createProjectFromConfig(config)

            // 4️⃣ Crear estructura + guardar
            val result = createProjectWithTemplate.execute(
                project = project,
                location = workspace,
                template = defaultTemplate
            )

            when (result) {

                is StructureGenerationResult.Success -> {

                    // 5️⃣ Guardar en recientes el PROJECT ROOT
                    val projectRoot = ProjectLocation(
                        (workspace.value.toPath() / projectName).toString()
                    )

                    recentProjectsRepo.recordCreated(projectRoot)

                    _state.update {
                        it.copy(
                            isCreatingProject = false,
                            recentProjects = recentProjectsRepo.load()
                        )
                    }

                    // 6️⃣ Navegar con contrato Core V2
                    onSuccess(workspace, projectName)
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
        projectName: String,
        onSuccess: (ProjectLocation, String) -> Unit
    ) {
        viewModelScope.launch {

            when (
                val result = validateExistingProject.execute(
                    location = location,
                    projectName = projectName
                )
            ) {

                ProjectValidationResult.Valid -> {
                    val updated = recentProjectsRepo.recordOpened(location)
                    _state.update { it.copy(recentProjects = updated) }

                    onSuccess(location, projectName)
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
                            "The selected workspace does not exist."

                        ProjectValidationResult.Invalid.MissingProjectFile ->
                            "This folder does not contain a valid AppMakeup project."

                        ProjectValidationResult.Invalid.InvalidProjectFile ->
                            "The project file is corrupted or unreadable."

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