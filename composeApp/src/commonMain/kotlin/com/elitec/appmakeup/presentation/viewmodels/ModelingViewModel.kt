package com.elitec.appmakeup.presentation.viewmodels

import androidx.lifecycle.ViewModel
import com.elitec.appmakeup.domain.codegen.CodeGenerator
import com.elitec.appmakeup.domain.model.EntityProperty
import com.elitec.appmakeup.domain.model.Feature
import com.elitec.appmakeup.domain.model.Project
import com.elitec.appmakeup.domain.usecase.AddEntityPropertyUseCase
import com.elitec.appmakeup.domain.usecase.AddFeatureUseCase
import com.elitec.appmakeup.domain.usecase.ValidateProjectUseCase
import com.elitec.appmakeup.presentation.uiStates.ModelingState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

class ModelingViewModel(
    private val addFeature: AddFeatureUseCase,
    private val addEntityProperty: AddEntityPropertyUseCase,
    private val validateProject: ValidateProjectUseCase,
    private val codeGenerator: CodeGenerator
) : ViewModel() {

    private val _state = MutableStateFlow(ModelingState())
    val state: StateFlow<ModelingState> = _state

    fun initialize(project: Project) {
        _state.value = ModelingState(
            project = project,
            selectedFeatureName = project.features.firstOrNull()?.name,
            isDirty = false
        )
    }

    fun addFeature(feature: Feature) {
        val project = requireProject()
        val updated = addFeature.execute(project, feature)

        _state.update {
            it.copy(
                project = updated,
                selectedFeatureName = feature.name,
                isDirty = true
            )
        }
    }

    fun addProperty(property: EntityProperty) {
        val project = requireProject()
        val featureName = _state.value.selectedFeatureName ?: return

        val updated = addEntityProperty.execute(
            project = project,
            featureName = featureName,
            property = property
        )

        _state.update { it.copy(project = updated, isDirty = true) }
    }

    fun generate() {
        val project = requireProject()

        val errors = validateProject.execute(project)
        if (errors.isNotEmpty()) {
            _state.update { it -> it.copy(validationErrors = errors.map { it }) }
            return
        }

        val tree = codeGenerator.generate(project)
        _state.update { it.copy(codeTree = tree, validationErrors = emptyList()) }
    }

    fun selectFeature(name: String) {
        _state.update { it.copy(selectedFeatureName = name) }
    }

    private fun requireProject(): Project =
        _state.value.project ?: error("Project not initialized")
}