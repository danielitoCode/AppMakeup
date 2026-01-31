package com.elitec.appmakeup.presentation.viewmodels

import androidx.lifecycle.ViewModel
import com.elitec.appmakeup.core.v5.domain.contracts.EditableRepositoryContract
import com.elitec.appmakeup.generation.GenerateCodeUseCase
import com.elitec.appmakeup.presentation.states.ProjectEditorUiState
import com.elitec.appmakeup.projects.model.AppEntity
import com.elitec.appmakeup.projects.model.AppFeature
import com.elitec.appmakeup.projects.model.AppMakeupProject
import com.elitec.appmakeup.projects.model.AppProperty
import com.elitec.appmakeup.projects.model.AppRelation
import com.elitec.appmakeup.projects.usecase.entity.AddEntityPropertyUseCase
import com.elitec.appmakeup.projects.usecase.entity.DeleteEntityPropertyUseCase
import com.elitec.appmakeup.projects.usecase.feature.AddFeatureUseCase
import com.elitec.appmakeup.projects.usecase.feature.RemoveFeatureUseCase
import com.elitec.appmakeup.projects.usecase.project.LoadProjectUseCase
import com.elitec.appmakeup.projects.usecase.project.SaveProjectUseCase
import com.elitec.appmakeup.projects.usecase.relation.AddRelationUseCase
import com.elitec.appmakeup.projects.usecase.relation.RemoveRelationUseCase
import com.elitec.appmakeup.projects.validations.ValidationIssue
import com.elitec.appmakeup.projects.validations.ValidationLevel
import com.elitec.appmakeup.projects.validations.ValidationScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

class ProjectEditorViewModel(
    private val loadProjectUseCase: LoadProjectUseCase,
    private val saveProjectUseCase: SaveProjectUseCase,
    private val addFeatureUseCase: AddFeatureUseCase,
    private val addEntityPropertyUseCase: AddEntityPropertyUseCase,
    private val deleteEntityPropertyUseCase: DeleteEntityPropertyUseCase,
    private val removeFeatureUseCase: RemoveFeatureUseCase,
    private val generateCodeUseCase: GenerateCodeUseCase,
    private val addRelationUseCase: AddRelationUseCase,
    private val removeRelationUseCase: RemoveRelationUseCase
) : ViewModel() {

    private val _uiState =
        MutableStateFlow(ProjectEditorUiState(isLoading = true))
    val uiState: StateFlow<ProjectEditorUiState> = _uiState

    /* ----------------------------------------------------
     * Load
     * ---------------------------------------------------- */

    fun loadProject(path: String) {
        try {
            val project = loadProjectUseCase.execute(path)
            refresh(project)
        } catch (e: Exception) {
            fail(e.message ?: "Error loading project")
        }
    }

    /* ----------------------------------------------------
     * Feature
     * ---------------------------------------------------- */

    fun addFeature(name: String) {
        val project = currentProject() ?: return

        if (project.features.any { it.name == name }) {
            fail("Feature '$name' already exists")
            return
        }

        val feature = AppFeature(
            name = name,
            entities = listOf(
                AppEntity(
                    name = "${name}Entity",
                    properties = listOf(
                        AppProperty(
                            name = "id",
                            type = "String",
                            isIdentifier = true
                        )
                    )
                )
            )
        )

        refresh(addFeatureUseCase.execute(project, feature))
    }

    fun removeFeature(name: String) {
        val project = currentProject() ?: return

        if (project.features.size <= 1) {
            fail("Project must contain at least one feature")
            return
        }

        refresh(removeFeatureUseCase.execute(project, name))
    }

    fun selectFeature(name: String) {
        val feature =
            currentProject()?.features?.find { it.name == name }

        _uiState.update {
            it.copy(selectedFeature = feature)
        }
    }

    /* ----------------------------------------------------
     * Entity
     * ---------------------------------------------------- */

    fun addEntity(featureName: String, entityName: String) {
        val project = currentProject() ?: return

        val updated = project.features.map { feature ->
            if (feature.name == featureName) {

                if (feature.entities.any { it.name == entityName }) {
                    fail("Entity '$entityName' already exists")
                    return
                }

                feature.copy(
                    entities = feature.entities + AppEntity(
                        name = entityName,
                        properties = listOf(
                            AppProperty(
                                name = "id",
                                type = "String",
                                isIdentifier = true
                            )
                        )
                    )
                )
            } else feature
        }

        refresh(project.copy(features = updated))
    }

    fun addEntityProperty(
        featureName: String,
        entityName: String,
        property: AppProperty
    ) {
        val project = currentProject() ?: return

        val updated = addEntityPropertyUseCase.execute(
            project,
            featureName,
            entityName,
            property
        )

        refresh(updated)
    }

    fun removeEntityProperty(
        featureName: String,
        entityName: String,
        property: String
    ) {
        val project = currentProject() ?: return

        val updated = deleteEntityPropertyUseCase.execute(
            project,
            featureName,
            entityName,
            property
        )

        refresh(updated)
    }

    /* ----------------------------------------------------
     * Repository contracts
     * ---------------------------------------------------- */

    fun updateRepositoryContract(
        featureName: String,
        contract: EditableRepositoryContract
    ) {
        val project = currentProject() ?: return

        val updatedFeatures = project.features.map { feature ->
            if (feature.name == featureName) {
                feature.copy(
                    repositoryContracts =
                        feature.repositoryContracts
                            .filterNot { it.entityName == contract.entityName } +
                                contract
                )
            } else feature
        }

        refresh(project.copy(features = updatedFeatures))
    }

    /* ----------------------------------------------------
     * Export
     * ---------------------------------------------------- */

    fun export(dryRun: Boolean) {
        val project = currentProject() ?: return

        val issues = validateProject(project)
        if (issues.isNotEmpty()) {
            _uiState.update {
                it.copy(validationIssues = issues)
            }
            return
        }

        generateCodeUseCase.execute(project, dryRun)
    }

    /* ----------------------------------------------------
     * Validation
     * ---------------------------------------------------- */

    private fun validateProject(project: AppMakeupProject): List<ValidationIssue> {
        val issues = mutableListOf<ValidationIssue>()

        if (project.features.isEmpty()) {
            issues += ValidationIssue(
                level = ValidationLevel.ERROR,
                scope = ValidationScope.PROJECT,
                message = "Project must contain at least one feature"
            )
            return issues
        }

        project.features.forEach { feature ->

            if (feature.entities.isEmpty()) {
                issues += ValidationIssue(
                    level = ValidationLevel.ERROR,
                    scope = ValidationScope.FEATURE,
                    feature = feature.name,
                    message = "Feature '${feature.name}' must contain at least one entity"
                )
            }

            val entityNames = feature.entities.map { it.name }.toSet()

            feature.entities.forEach { entity ->

                if (entity.properties.isEmpty()) {
                    issues += ValidationIssue(
                        level = ValidationLevel.ERROR,
                        scope = ValidationScope.ENTITY,
                        feature = feature.name,
                        entity = entity.name,
                        message = "Entity '${entity.name}' must have at least one property"
                    )
                }

                val ids = entity.properties.count { it.isIdentifier }
                if (ids != 1) {
                    issues += ValidationIssue(
                        level = ValidationLevel.ERROR,
                        scope = ValidationScope.ENTITY,
                        feature = feature.name,
                        entity = entity.name,
                        message = "Entity '${entity.name}' must have exactly one identifier"
                    )
                }
            }

            feature.repositoryContracts.forEach { contract ->
                if (contract.entityName !in entityNames) {
                    issues += ValidationIssue(
                        level = ValidationLevel.ERROR,
                        scope = ValidationScope.REPOSITORY,
                        feature = feature.name,
                        entity = contract.entityName,
                        message = "RepositoryContract entity '${contract.entityName}' does not exist"
                    )
                }

                if (!contract.isValid()) {
                    issues += ValidationIssue(
                        level = ValidationLevel.ERROR,
                        scope = ValidationScope.REPOSITORY,
                        feature = feature.name,
                        entity = contract.entityName,
                        message = "RepositoryContract must support at least one operation"
                    )
                }
            }
        }

        return issues
    }

    /* ----------------------------------------------------
     * Helpers
     * ---------------------------------------------------- */

    private fun refresh(project: AppMakeupProject) {
        val issues = validateProject(project)

        _uiState.update {
            it.copy(
                project = project,
                features = project.features,
                validationIssues = issues,
                canExport = issues.none { it.level == ValidationLevel.ERROR },
                isLoading = false,
                error = null
            )
        }

        saveProjectUseCase.execute(project)
    }

    private fun fail(message: String) {
        _uiState.update {
            it.copy(isLoading = false, error = message)
        }
    }

    fun selectEntity(entityName: String) {
        val feature = _uiState.value.selectedFeature ?: return
        val entity = feature.entities.find { it.name == entityName }

        _uiState.update {
            it.copy(selectedEntity = entity)
        }
    }

    fun addRelation(
        featureName: String,
        relation: AppRelation
    ) {
        val project = currentProject() ?: return

        val updated = addRelationUseCase.execute(
            project,
            featureName,
            relation
        )

        refresh(updated)

        println(
            "[VM] Relations in feature '$featureName': " +
                    updated.features
                        .first { it.name == featureName }
                        .relations
        )
    }

    fun removeRelation(
        featureName: String,
        relation: AppRelation
    ) {
        val project = currentProject() ?: return

        val updated = removeRelationUseCase.execute(
            project,
            featureName,
            relation
        )

        refresh(updated)
    }

    private fun currentProject(): AppMakeupProject? =
        _uiState.value.project
}