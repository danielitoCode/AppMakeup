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

    override fun save(location: ProjectLocation, project: Project) {
        val root = location.value.toPath() / project.name
        fileSystem.createDirectories(root)

        val projectFile = root / "project.json"
        val json = serializer.serialize(project)

        fileSystem.write(projectFile) {
            writeUtf8(json)
        }
    }

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