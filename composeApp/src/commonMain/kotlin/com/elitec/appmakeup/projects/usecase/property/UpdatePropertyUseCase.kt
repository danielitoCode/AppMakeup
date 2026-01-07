package com.elitec.appmakeup.projects.usecase.property

import com.elitec.appmakeup.projects.model.AppMakeupProject
import com.elitec.appmakeup.projects.model.AppProperty
import com.elitec.appmakeup.projects.persistence.ProjectPersistence

class UpdatePropertyUseCase(
    private val persistence: ProjectPersistence
) {

    fun execute(
        project: AppMakeupProject,
        featureName: String,
        propertyName: String,
        updatedProperty: AppProperty
    ): AppMakeupProject {

        val updated = project.copy(
            features = project.features.map { feature ->
                if (feature.name == featureName) {
                    feature.copy(
                        properties = feature.properties.map {
                            if (it.name == propertyName) updatedProperty else it
                        }
                    )
                } else feature
            }
        )

        persistence.save(updated)
        return updated
    }
}