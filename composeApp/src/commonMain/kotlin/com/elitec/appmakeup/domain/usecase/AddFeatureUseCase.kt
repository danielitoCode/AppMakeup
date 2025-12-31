package com.elitec.appmakeup.domain.usecase

import com.elitec.appmakeup.domain.modeling.Feature
import com.elitec.appmakeup.domain.project.Project
import com.elitec.appmakeup.domain.project.ProjectLocation
import com.elitec.appmakeup.domain.repository.ProjectRepository

class AddFeatureUseCase(
    private val repository: ProjectRepository
) {

    fun execute(location: ProjectLocation,project: Project, feature: Feature): Project {
        val updated = project.copy(
            features = project.features + feature
        )
        repository.save(location,updated)
        return updated
    }
}