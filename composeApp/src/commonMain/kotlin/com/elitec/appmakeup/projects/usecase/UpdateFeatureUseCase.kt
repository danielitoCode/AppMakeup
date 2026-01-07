package com.elitec.appmakeup.projects.usecase

import com.elitec.appmakeup.projects.model.AppFeature
import com.elitec.appmakeup.projects.model.AppMakeupProject
import com.elitec.appmakeup.projects.persistence.ProjectPersistence

class UpdateFeatureUseCase(
    private val persistence: ProjectPersistence
) {

    fun execute(
        project: AppMakeupProject,
        featureName: String,
        updatedFeature: AppFeature
    ): AppMakeupProject {

        val updated = project.copy(
            features = project.features.map {
                if (it.name == featureName) updatedFeature else it
            }
        )

        persistence.save(updated)
        return updated
    }
}