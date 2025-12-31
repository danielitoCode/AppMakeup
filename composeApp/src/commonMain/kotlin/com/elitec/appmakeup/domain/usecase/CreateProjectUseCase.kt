package com.elitec.appmakeup.domain.usecase

import com.elitec.appmakeup.domain.project.Project
import com.elitec.appmakeup.domain.repository.ProjectRepository

class CreateProjectUseCase(
    private val repository: ProjectRepository
) {
    fun execute(project: Project) {
        repository.save(project)
    }
}