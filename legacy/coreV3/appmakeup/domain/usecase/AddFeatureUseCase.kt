package com.elitec.appmakeup.domain.usecase

import com.elitec.appmakeup.domain.model.Feature
import com.elitec.appmakeup.domain.model.Project

class AddFeatureUseCase {
    fun execute(
        project: Project,
        feature: Feature
    ): Project {
        return project.copy(
            features = project.features + feature
        )
    }
}