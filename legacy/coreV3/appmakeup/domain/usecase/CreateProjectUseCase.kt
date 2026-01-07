package com.elitec.appmakeup.domain.usecase

import com.elitec.appmakeup.domain.model.Project
import com.elitec.appmakeup.domain.model.ProjectLocation
import com.elitec.appmakeup.domain.model.ProjectRepository
import com.elitec.appmakeup.infrastructure.persistence.ProjectPersistenceAdapter

/*class CreateProjectUseCase {

    fun execute(project: Project): Project {
        // Core V3: solo devuelve el proyecto creado
        return project
    }
}*/

class CreateProjectUseCase(
    private val repository: ProjectRepository
) {
    fun execute(
        location: ProjectLocation,
        project: Project
    ) {
        repository.save(location, project)
    }
}