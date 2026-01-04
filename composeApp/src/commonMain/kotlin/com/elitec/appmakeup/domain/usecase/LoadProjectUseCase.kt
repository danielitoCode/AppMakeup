package com.elitec.appmakeup.domain.usecase

import com.elitec.appmakeup.domain.model.Project
import com.elitec.appmakeup.domain.model.ProjectLocation
import com.elitec.appmakeup.domain.model.ProjectRepository

class LoadProjectUseCase(
    private val repository: ProjectRepository
) {
    /**
     * Loads an existing project from disk.
     * Returns null if not found or invalid.
     */
    fun execute(location: ProjectLocation): Project? {
        return repository.load(location)
    }
}