package com.elitec.appmakeup.data.filesystem

import com.elitec.appmakeup.domain.model.Project
import com.elitec.appmakeup.domain.model.ProjectLocation
import com.elitec.appmakeup.domain.model.ProjectRepository
import okio.FileSystem
import okio.Path.Companion.toPath

class LocalProjectRepository(
    private val fileSystem: FileSystem,
    private val serializer: ProjectSerializer
) : ProjectRepository {

    override fun load(location: ProjectLocation): Project? {
        val projectFile = location.value.toPath() / "project.json"

        if (!fileSystem.exists(projectFile)) return null

        return try {
            val content = fileSystem.read(projectFile) { readUtf8() }
            serializer.deserialize(content)
        } catch (e: Exception) {
            null
        }
    }
}