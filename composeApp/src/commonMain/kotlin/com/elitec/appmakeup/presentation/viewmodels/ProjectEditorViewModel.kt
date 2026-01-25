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
    private val listFeaturesUseCase: ListFeaturesUseCase,
    private val getFeatureUseCase: GetFeatureUseCase,
    private val listPropertiesUseCase: ListPropertiesUseCase,
    private val addFeatureUseCase: AddFeatureUseCase,
    private val removeFeatureUseCase: RemoveFeatureUseCase,
    private val addPropertyUseCase: AddPropertyUseCase,
    private val removePropertyUseCase: RemovePropertyUseCase,
    private val generateCodeUseCase: GenerateCodeUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(ProjectEditorUiState(isLoading = true))
    val uiState: StateFlow<ProjectEditorUiState> = _uiState

    /* ---------------------------
     * Project lifecycle
     * --------------------------- */

    fun loadProject(path: String) {
        try {
            val project = loadProjectUseCase.execute(path)
            refreshProject(project)
        } catch (e: Exception) {
            _uiState.update {
                it.copy(isLoading = false, error = e.message)
            }
        }
    }

    /* ---------------------------
     * Feature management
     * --------------------------- */

    fun addFeature(name: String) {
        val project = currentProject() ?: return

        val updated = addFeatureUseCase.execute(
            project,
            AppFeature(
                name = name,
                properties = emptyList()
            )
        )

        persistAndRefresh(updated)
        selectFeature(name)
    }

    fun removeFeature(name: String) {
        val project = currentProject() ?: return

        val updated = removeFeatureUseCase.execute(project, name)
        persistAndRefresh(updated)

        // si borraste la seleccionada, limpia selección
        _uiState.update { it.copy(selectedFeature = null, properties = emptyList()) }
    }

    fun selectFeature(name: String) {
        val project = currentProject() ?: return

        val feature = getFeatureUseCase.execute(project, name)
        val properties = feature?.let {
            listPropertiesUseCase.execute(project, it.name)
        }.orEmpty()

        _uiState.update {
            it.copy(
                selectedFeature = feature,
                properties = properties
            )
        }
    }

    /* ---------------------------
     * Property management
     * --------------------------- */

    fun addProperty(
        featureName: String,
        propertyName: String,
        type: String,
        isIdentifier: Boolean = false
    ) {
        val project = currentProject() ?: return

        val updated = addPropertyUseCase.execute(
            project,
            featureName,
            AppProperty(
                name = propertyName,
                type = type,
                isIdentifier = isIdentifier
            )
        )

        persistAndRefresh(updated)
        selectFeature(featureName)
    }

    fun removeProperty(featureName: String, propertyName: String) {
        val project = currentProject() ?: return

        val updated = removePropertyUseCase.execute(
            project,
            featureName,
            propertyName
        )

        persistAndRefresh(updated)
        selectFeature(featureName)
    }

    /* ---------------------------
     * Code generation / Export
     * --------------------------- */

    fun exportProject() {
        val project = currentProject() ?: return

        _uiState.update { it.copy(isExporting = true, error = null) }

        try {
            generateCodeUseCase.execute(project, dryRun = false)
        } catch (e: Exception) {
            _uiState.update { it.copy(error = e.message) }
        } finally {
            _uiState.update { it.copy(isExporting = false) }
        }
    }

    /* ---------------------------
     * Helpers
     * --------------------------- */

    private fun persistAndRefresh(project: AppMakeupProject) {
        saveProjectUseCase.execute(project)
        refreshProject(project)
    }

    private fun refreshProject(project: AppMakeupProject) {
        val features = listFeaturesUseCase.execute(project)

        _uiState.update {
            it.copy(
                project = project,
                features = features,
                isLoading = false,
                error = null
            )
        }
    }

    private fun currentProject(): AppMakeupProject? =
        _uiState.value.project
}