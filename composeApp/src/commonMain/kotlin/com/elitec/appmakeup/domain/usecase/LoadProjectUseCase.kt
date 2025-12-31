package com.elitec.appmakeup.domain.usecase

import com.elitec.appmakeup.domain.project.Project
import com.elitec.appmakeup.domain.project.ProjectLocation
import com.elitec.appmakeup.domain.repository.ProjectRepository

class LoadProjectUseCase(
    private val repository: ProjectRepository
) {
    fun execute(location: ProjectLocation): Project? {
        // Ajustá esto a tu contrato real:
        // si tu repo usa projectId o projectName, adapta aquí.
        return repository.load(location)
    }
}