package com.elitec.appmakeup.domain.usecase

import com.elitec.appmakeup.domain.project.Project
import com.elitec.appmakeup.domain.project.ProjectLocation
import com.elitec.appmakeup.domain.repository.ProjectRepository

class LoadProjectUseCase(
    private val repository: ProjectRepository
) {
    fun execute(
        location: ProjectLocation,
        projectName: String
    ): Project? {
        return repository.load(
            location = location,
            projectName = projectName
        )
    }
}