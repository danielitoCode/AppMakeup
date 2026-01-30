package com.elitec.appmakeup.projects.usecase.property

import com.elitec.appmakeup.projects.model.AppMakeupProject
import com.elitec.appmakeup.projects.persistence.ProjectPersistence

class DeleteEntityPropertyUseCase(
    private val persistence: ProjectPersistence
) {

    fun execute(
        project: AppMakeupProject,
        featureName: String,
        entityName: String,
        propertyName: String
    ): AppMakeupProject {

        val updated = project.copy(
            features = project.features.map { feature ->
                if (feature.name == featureName) {

                    feature.copy(
                        entities = feature.entities.map { entity ->
                            if (entity.name == entityName) {

                                entity.copy(
                                    properties = entity.properties.filterNot {
                                        it.name == propertyName
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