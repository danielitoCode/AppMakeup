package com.elitec.appmakeup.projects.usecase.project

import com.elitec.appmakeup.recent.RecentProjectsRepository

class ListRecentProjectsUseCase(
    private val recentProjects: RecentProjectsRepository
) {

    fun execute(): List<String> =
        recentProjects.list()
}