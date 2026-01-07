package com.elitec.appmakeup.projects.usecase.property

import com.elitec.appmakeup.projects.model.AppMakeupProject
import com.elitec.appmakeup.projects.persistence.ProjectPersistence

class RemovePropertyUseCase(
    private val persistence: ProjectPersistence
) {

    fun execute(
        project: AppMakeupProject,
        featureName: String,
        propertyName: String
    ): AppMakeupProject {

        val updated = project.copy(
            features = project.features.map { feature ->
                if (feature.name == featureName) {
                    feature.copy(
                        properties = feature.properties.filterNot {
                            it.name == propertyName
                        }
                    )
                } else feature
            }
        )

        persistence.save(updated)
        return updated
    }
}