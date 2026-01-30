package com.elitec.appmakeup.projects.usecase.property

import com.elitec.appmakeup.projects.model.AppMakeupProject
import com.elitec.appmakeup.projects.model.AppProperty
import com.elitec.appmakeup.projects.persistence.ProjectPersistence

class AddEntityPropertyUseCase(
    private val persistence: ProjectPersistence
) {

    fun execute(
        project: AppMakeupProject,
        featureName: String,
        entityName: String,
        property: AppProperty
    ): AppMakeupProject {

        val updated = project.copy(
            features = project.features.map { feature ->
                if (feature.name != featureName) return@map feature

                val updatedEntities = feature.entities.map { entity ->
                    if (entity.name != entityName) return@map entity

                    // Evitar duplicados por nombre
                    if (entity.properties.any { it.name == property.name }) {
                        return@map entity
                    }

                    // Si es identifier, quitar identifier previo (opcional)
                    val normalizedProps =
                        if (property.isIdentifier)
                            entity.properties.map { it.copy(isIdentifier = false) }
                        else
                            entity.properties

                    entity.copy(
                        properties = normalizedProps + property
                    )
                }

                feature.copy(entities = updatedEntities)
            }
        )

        persistence.save(updated)
        return updated
    }
}