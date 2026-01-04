package com.elitec.appmakeup.domain.usecase

import com.elitec.appmakeup.domain.model.Project
import com.elitec.appmakeup.domain.model.ProjectLocation
import com.elitec.appmakeup.infrastructure.di.persistence.ProjectPersistenceAdapter

/*class CreateProjectUseCase {

    fun execute(project: Project): Project {
        // Core V3: solo devuelve el proyecto creado
        return project
    }
}*/

class CreateProjectUseCase(
    private val persistence: ProjectPersistenceAdapter
) {

    fun execute(
        location: ProjectLocation,
        project: Project
    ) {
        persistence.save(location, project)
    }
}