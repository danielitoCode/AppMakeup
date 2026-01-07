package com.elitec.appmakeup.projects.usecase

import com.elitec.appmakeup.projects.model.AppMakeupProject
import com.elitec.appmakeup.projects.model.AppProperty
import com.elitec.appmakeup.projects.persistence.ProjectPersistence

class AddPropertyUseCase(
    private val persistence: ProjectPersistence
) {

    fun execute(
        project: AppMakeupProject,
        featureName: String,
        property: AppProperty
    ): AppMakeupProject {

        val updated = project.copy(
            features = project.features.map { feature ->
                if (feature.name == featureName) {
                    feature.copy(
                        properties = feature.properties + property
                    )
                } else feature
            }
        )

        persistence.save(updated)
        return updated
    }
}