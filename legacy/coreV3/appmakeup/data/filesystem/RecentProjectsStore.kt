package com.elitec.appmakeup.data.filesystem

import com.elitec.appmakeup.domain.model.ProjectLocation
import okio.FileSystem
import okio.Path.Companion.toPath

class RecentProjectsStore(
    private val fileSystem: FileSystem
) {
    private val file = "recent-projects.json".toPath()

    fun load(): List<ProjectLocation> {
        if (!fileSystem.exists(file)) return emptyList()
        return fileSystem.read(file) {
            readUtf8().lines().filter { it.isNotBlank() }
        }.map(::ProjectLocation)
    }

    fun save(projects: List<ProjectLocation>) {
        fileSystem.write(file) {
            writeUtf8(projects.joinToString("\n") { it.value })
        }
    }
}