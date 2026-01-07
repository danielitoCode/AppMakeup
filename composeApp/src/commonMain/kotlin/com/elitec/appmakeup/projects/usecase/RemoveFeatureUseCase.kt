package com.elitec.appmakeup.projects.usecase

import com.elitec.appmakeup.projects.model.AppMakeupProject
import com.elitec.appmakeup.projects.persistence.ProjectPersistence

class RemoveFeatureUseCase(
    private val persistence: ProjectPersistence
) {

    fun execute(
        project: AppMakeupProject,
        featureName: String
    ): AppMakeupProject {

        val updated = project.copy(
            features = project.features.filterNot { it.name == featureName }
        )

        persistence.save(updated)
        return updated
    }
}