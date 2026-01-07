package com.elitec.appmakeup.infrastructure.recent

import com.elitec.appmakeup.domain.model.ProjectLocation
import com.elitec.appmakeup.domain.recent.RecentProjectsStore
import kotlinx.serialization.json.Json
import okio.FileSystem
import okio.Path.Companion.toPath

class FileRecentProjectsStore(
    private val fileSystem: FileSystem,
    private val json: Json
) : RecentProjectsStore {

    private val filePath = "recent-projects.json".toPath()

    override fun load(): List<ProjectLocation> {
        if (!fileSystem.exists(filePath)) return emptyList()

        return try {
            val content = fileSystem.read(filePath) { readUtf8() }
            val raw = json.decodeFromString<List<String>>(content)
            raw.map { ProjectLocation(it) }
        } catch (_: Exception) {
            emptyList()
        }
    }

    override fun save(projects: List<ProjectLocation>) {
        val content = json.encodeToString(
            projects.map { it.value }
        )

        fileSystem.write(filePath) {
            writeUtf8(content)
        }
    }
}