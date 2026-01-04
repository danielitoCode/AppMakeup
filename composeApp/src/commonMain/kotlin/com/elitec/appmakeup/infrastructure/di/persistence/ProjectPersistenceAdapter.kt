package com.elitec.appmakeup.infrastructure.di.persistence

import com.elitec.appmakeup.domain.model.Project
import com.elitec.appmakeup.domain.model.ProjectLocation
import com.elitec.appmakeup.domain.model.ProjectRepository

class ProjectPersistenceAdapter(
    private val repository: ProjectRepository
) {

    fun save(
        location: ProjectLocation,
        project: Project
    ) {
        repository.save(location, project)
    }

    fun load(
        location: ProjectLocation
    ): Project? =
        repository.load(location)
}