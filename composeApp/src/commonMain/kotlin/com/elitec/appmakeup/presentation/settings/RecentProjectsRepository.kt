package com.elitec.appmakeup.presentation.settings

import com.elitec.appmakeup.domain.project.ProjectLocation

class RecentProjectsRepository(
    private val store: AppSettingsStore
) {
    fun load(): List<ProjectLocation> =
        store.load()
            .recentProjects
            .map { ProjectLocation(it).normalized() }

    fun recordOpened(location: ProjectLocation): List<ProjectLocation> {
        return saveInternal(location)
    }

    fun recordCreated(location: ProjectLocation): List<ProjectLocation> {
        return saveInternal(location)
    }

    private fun saveInternal(location: ProjectLocation): List<ProjectLocation> {
        val normalized = location.normalized()

        val updated = (
                listOf(normalized.value) +
                        store.load().recentProjects
                )
            .distinct()
            .take(10)

        store.save(AppSettings(updated))

        return updated.map { ProjectLocation(it) }
    }
}