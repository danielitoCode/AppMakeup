package com.elitec.appmakeup.domain.project.usecase

import com.elitec.appmakeup.domain.project.Project
import com.elitec.appmakeup.domain.project.ProjectLocation
import com.elitec.appmakeup.domain.repository.ProjectRepository

class SaveProjectUseCase(
    private val repository: ProjectRepository
) {

    fun execute(project: Project, location: ProjectLocation) {
        repository.save(location, project)
    }
}