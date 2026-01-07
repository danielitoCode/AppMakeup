package com.elitec.appmakeup.projects.usecase

import com.elitec.appmakeup.projects.model.AppMakeupProject
import com.elitec.appmakeup.projects.persistence.ProjectPersistence

class SaveProjectUseCase(
    private val persistence: ProjectPersistence
) {

    fun execute(project: AppMakeupProject) {
        persistence.save(project)
    }
}