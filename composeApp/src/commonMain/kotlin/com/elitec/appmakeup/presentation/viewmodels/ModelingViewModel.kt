package com.elitec.appmakeup.presentation.viewmodels

import androidx.lifecycle.ViewModel
import com.elitec.appmakeup.domain.modeling.Feature
import com.elitec.appmakeup.domain.modeling.entity.EntityProperty
import com.elitec.appmakeup.domain.project.Project
import com.elitec.appmakeup.domain.project.ProjectLocation
import com.elitec.appmakeup.domain.usecase.AddEntityPropertyUseCase
import com.elitec.appmakeup.domain.usecase.AddFeatureUseCase
import com.elitec.appmakeup.domain.usecase.CreateProjectUseCase
import com.elitec.appmakeup.domain.usecase.GenerateProjectStructureUseCase
import com.elitec.appmakeup.domain.usecase.ValidateProjectUseCase
import com.elitec.appmakeup.presentation.uiStates.ModelingState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

class ModelingViewModel(
    private val createProject: CreateProjectUseCase,
    private val addFeature: AddFeatureUseCase,
    private val addEntityProperty: AddEntityPropertyUseCase,
    private val validateProject: ValidateProjectUseCase,
    private val generateStructure: GenerateProjectStructureUseCase
): ViewModel() {

    private val _state = MutableStateFlow(ModelingState())
    val state: StateFlow<ModelingState> = _state

    // -------------------------
    // Project lifecycle
    // -------------------------

    fun createProject(project: Project, location: ProjectLocation) {
        createProject.execute(location, project)

        _state.value = ModelingState(
            project = project,
            location = location,
            isDirty = false
        )
    }

    // -------------------------
    // Feature / Entity modeling
    // -------------------------

    fun addFeature(feature: Feature) {
        val current = requireProjectAndLocation()

        val updated = addFeature.execute(
            location = current.location,
            project = current.project,
            feature = feature
        )

        updateProject(updated)
    }

    fun addProperty(
        featureName: String,
        property: EntityProperty
    ) {
        val current = requireProjectAndLocation()

        val updated = addEntityProperty.execute(
            location = current.location,
            project = current.project,
            featureName = featureName,
            property = property
        )

        updateProject(updated)
    }

    // -------------------------
    // Persistence & generation
    // -------------------------

    fun save() {
        val current = requireProjectAndLocation()

        createProject.execute(current.location, current.project)

        _state.update {
            it.copy(isDirty = false)
        }
    }

    fun generate() {
        val current = requireProjectAndLocation()

        val violations = validateProject.execute(current.project)

        if (violations.isNotEmpty()) {
            _state.update {
                it.copy(validationErrors = violations)
            }
            return
        }

        generateStructure.execute(
            location = current.location,
            project = current.project
        )

        _state.update {
            it.copy(validationErrors = emptyList())
        }
    }

    // -------------------------
    // Internal helpers
    // -------------------------

    private fun updateProject(project: Project) {
        _state.update {
            it.copy(
                project = project,
                isDirty = true,
                validationErrors = emptyList()
            )
        }
    }

    private fun requireProjectAndLocation(): ModelingContext {
        val state = _state.value

        val project = state.project
            ?: error("Project not initialized")

        val location = state.location
            ?: error("ProjectLocation not set")

        return ModelingContext(project, location)
    }

    private data class ModelingContext(
        val project: Project,
        val location: ProjectLocation
    )
}