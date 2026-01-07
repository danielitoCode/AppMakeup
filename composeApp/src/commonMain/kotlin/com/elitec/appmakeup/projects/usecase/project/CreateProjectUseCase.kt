package com.elitec.appmakeup.projects.usecase.project

import com.elitec.appmakeup.projects.model.AppMakeupProject
import com.elitec.appmakeup.projects.persistence.ProjectPersistence
import com.elitec.appmakeup.recent.RecentProjectsRepository

class CreateProjectUseCase(
    private val projectPersistence: ProjectPersistence,
    private val recentProjects: RecentProjectsRepository
) {

    fun execute(project: AppMakeupProject) {
        projectPersistence.save(project)
        recentProjects.add(project.path)
    }
}