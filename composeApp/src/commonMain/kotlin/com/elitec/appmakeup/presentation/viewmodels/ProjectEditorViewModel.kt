package com.elitec.appmakeup.presentation.viewmodels

import androidx.lifecycle.ViewModel
import com.elitec.appmakeup.core.v4.definition.CoreEntity
import com.elitec.appmakeup.core.v4.definition.CoreLayer
import com.elitec.appmakeup.core.v4.definition.CoreProperty
import com.elitec.appmakeup.generation.GenerateCodeUseCase
import com.elitec.appmakeup.presentation.states.ProjectEditorUiState
import com.elitec.appmakeup.projects.model.AppFeature
import com.elitec.appmakeup.projects.model.AppMakeupProject
import com.elitec.appmakeup.projects.model.AppProperty
import com.elitec.appmakeup.projects.usecase.feature.AddFeatureUseCase
import com.elitec.appmakeup.projects.usecase.feature.GetFeatureUseCase
import com.elitec.appmakeup.projects.usecase.feature.ListFeaturesUseCase
import com.elitec.appmakeup.projects.usecase.feature.RemoveFeatureUseCase
import com.elitec.appmakeup.projects.usecase.project.LoadProjectUseCase
import com.elitec.appmakeup.projects.usecase.project.SaveProjectUseCase
import com.elitec.appmakeup.projects.usecase.property.AddPropertyUseCase
import com.elitec.appmakeup.projects.usecase.property.ListPropertiesUseCase
import com.elitec.appmakeup.projects.usecase.property.RemovePropertyUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

class ProjectEditorViewModel(
    private val loadProjectUseCase: LoadProjectUseCase,
    private val saveProjectUseCase: SaveProjectUseCase,
    private val addFeatureUseCase: AddFeatureUseCase,
    private val removeFeatureUseCase: RemoveFeatureUseCase,
    private val addPropertyUseCase: AddPropertyUseCase,
    private val removePropertyUseCase: RemovePropertyUseCase,
    private val generateCodeUseCase: GenerateCodeUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(ProjectEditorUiState(isLoading = true))
    val uiState: StateFlow<ProjectEditorUiState> = _uiState

    /* ---------------------------
     * Load
     * --------------------------- */

    fun loadProject(path: String) {
        try {
            val project = loadProjectUseCase.execute(path)
            refresh(project)
        } catch (e: Exception) {
            _uiState.update {
                it.copy(isLoading = false, error = e.message)
            }
        }
    }

    /* ---------------------------
     * Feature
     * --------------------------- */

    fun addFeature(name: String) {
        val project = currentProject() ?: return

        if (project.features.any { it.name == name }) {
            fail("Feature '$name' already exists")
            return
        }

        val feature = AppFeature(
            name = name,
            properties = listOf(
                AppProperty(
                    name = "id",
                    type = "String",
                    isIdentifier = true
                )
            )
        )

        refresh(addFeatureUseCase.execute(project, feature))
    }

    fun removeFeature(name: String) {
        val project = currentProject() ?: return

        if (project.features.size == 1) {
            fail("Project must contain at least one feature")
            return
        }

        refresh(removeFeatureUseCase.execute(project, name))
    }

    fun selectFeature(name: String) {
        val feature = currentProject()?.features?.find { it.name == name }
        _uiState.update { it.copy(selectedFeature = feature) }
    }

    /* ---------------------------
     * Properties
     * --------------------------- */

    fun addProperty(
        featureName: String,
        propertyName: String,
        type: String,
        isIdentifier: Boolean
    ) {
        val project = currentProject() ?: return

        val feature = project.features.find { it.name == featureName } ?: return

        if (isIdentifier && feature.properties.any { it.isIdentifier }) {
            fail("Feature '$featureName' already has an identifier")
            return
        }

        refresh(
            addPropertyUseCase.execute(
                project,
                featureName,
                AppProperty(propertyName, type, isIdentifier)
            )
        )
    }

    fun removeProperty(featureName: String, propertyName: String) {
        val project = currentProject() ?: return
        val feature = project.features.find { it.name == featureName } ?: return

        val property = feature.properties.find { it.name == propertyName } ?: return

        if (property.isIdentifier) {
            fail("An identifier property cannot be removed")
            return
        }

        refresh(removePropertyUseCase.execute(project, featureName, propertyName))
    }

    /* ---------------------------
     * Export
     * --------------------------- */

    fun export() {
        val project = currentProject() ?: return

        val errors = validateProject(project)
        if (errors.isNotEmpty()) {
            _uiState.update { it.copy(validationErrors = errors) }
            return
        }

        generateCodeUseCase.execute(project, true)
    }

    /* ---------------------------
     * Helpers
     * --------------------------- */

    private fun refresh(project: AppMakeupProject) {
        val errors = validateProject(project)

        _uiState.update {
            it.copy(
                project = project,
                features = project.features,
                selectedFeature = null,
                validationErrors = errors,
                canExport = errors.isEmpty(),
                isLoading = false,
                error = null
            )
        }

        saveProjectUseCase.execute(project)
    }

    private fun fail(message: String) {
        _uiState.update { it.copy(error = message) }
    }

    private fun currentProject(): AppMakeupProject? =
        _uiState.value.project

    private fun validateProject(project: AppMakeupProject): List<String> {
        val errors = mutableListOf<String>()

        if (project.features.isEmpty()) {
            errors += "Project must contain at least one feature"
        }

        project.features.forEach { feature ->
            if (feature.properties.isEmpty()) {
                errors += "Feature '${feature.name}' must have at least one property"
            }

            val identifiers = feature.properties.count { it.isIdentifier }
            if (identifiers != 1) {
                errors += "Feature '${feature.name}' must have exactly one identifier property"
            }
        }

        return errors
    }

    private fun validateFeature(feature: AppFeature): String? {

        val identifiers =
            feature.properties.count { it.isIdentifier }

        if (identifiers != 1) {
            return "Feature '${feature.name}' must have exactly one identifier"
        }

        feature.repository?.let {
            if (!it.isValid()) {
                return "Repository for '${feature.name}' must support at least one operation"
            }
        }

        return null
    }

    fun canExport(): Boolean =
        _uiState.value.features.all { validateFeature(it) == null }
}