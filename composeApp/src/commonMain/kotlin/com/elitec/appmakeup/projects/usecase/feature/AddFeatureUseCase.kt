package com.elitec.appmakeup.projects.usecase.feature

import com.elitec.appmakeup.projects.model.AppFeature
import com.elitec.appmakeup.projects.model.AppMakeupProject
import com.elitec.appmakeup.projects.persistence.ProjectPersistence

class AddFeatureUseCase(
    private val persistence: ProjectPersistence
) {

    fun execute(
        project: AppMakeupProject,
        feature: AppFeature
    ): AppMakeupProject {

        val updated = project.copy(
            features = project.features + feature
        )

        persistence.save(updated)
        return updated
    }
}