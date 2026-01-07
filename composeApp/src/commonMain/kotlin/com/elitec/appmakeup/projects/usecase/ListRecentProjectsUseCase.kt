package com.elitec.appmakeup.projects.usecase

import com.elitec.appmakeup.recent.RecentProjectsRepository

class ListRecentProjectsUseCase(
    private val recentProjects: RecentProjectsRepository
) {

    fun execute(): List<String> =
        recentProjects.list()
}