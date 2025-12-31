package com.elitec.appmakeup.domain.usecase

import com.elitec.appmakeup.domain.modeling.entity.EntityProperty
import com.elitec.appmakeup.domain.project.Project
import com.elitec.appmakeup.domain.repository.ProjectRepository

class AddEntityPropertyUseCase(
    private val repository: ProjectRepository
) {

    fun execute(
        project: Project,
        featureName: String,
        property: EntityProperty
    ): Project {
        val updatedFeatures = project.features.map { feature ->
            if (feature.name == featureName) {
                val updatedEntity = feature.entity.copy(
                    properties = feature.entity.properties + property
                )
                feature.copy(entity = updatedEntity)
            } else feature
        }

        val updatedProject = project.copy(features = updatedFeatures)
        repository.save(updatedProject)
        return updatedProject
    }
}