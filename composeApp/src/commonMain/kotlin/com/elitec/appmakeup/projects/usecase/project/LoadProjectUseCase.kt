package com.elitec.appmakeup.projects.usecase.project

import com.elitec.appmakeup.projects.model.AppMakeupProject
import com.elitec.appmakeup.projects.persistence.ProjectPersistence
import com.elitec.appmakeup.recent.RecentProjectsRepository

class LoadProjectUseCase(
    private val projectPersistence: ProjectPersistence,
    private val recentProjects: RecentProjectsRepository
) {

    fun execute(path: String): AppMakeupProject {
        val project = projectPersistence.load(path)
        recentProjects.add(path)
        return project
    }
}