package com.elitec.appmakeup.domain.usecase

import com.elitec.appmakeup.domain.project.ProjectLocation
import com.elitec.appmakeup.domain.repository.ProjectRepository

class ValidateExistingProjectUseCase(
    private val projectRepository: ProjectRepository
) {
    fun execute(location: ProjectLocation): Boolean {
        return projectRepository.load(location) != null
    }
}