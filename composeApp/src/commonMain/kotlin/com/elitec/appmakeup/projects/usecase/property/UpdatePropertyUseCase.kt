package com.elitec.appmakeup.projects.usecase.property

import com.elitec.appmakeup.projects.model.AppMakeupProject
import com.elitec.appmakeup.projects.model.AppProperty
import com.elitec.appmakeup.projects.persistence.ProjectPersistence

class UpdateEntityPropertyUseCase(
    private val persistence: ProjectPersistence
) {

    fun execute(
        project: AppMakeupProject,
        featureName: String,
        entityName: String,
        propertyName: String,
        updatedProperty: AppProperty
    ): AppMakeupProject {

        val updated = project.copy(
            features = project.features.map { feature ->
                if (feature.name == featureName) {

                    feature.copy(
                        entities = feature.entities.map { entity ->
                            if (entity.name == entityName) {

                                entity.copy(
                                    properties = entity.properties.map { property ->
                                        if (property.name == propertyName)
                                            updatedProperty
                                        else
                                            property
                                    }
                                )
                            } else entity
                        }
                    )
                } else feature
            }
        )

        persistence.save(updated)
        return updated
    }
}