package com.elitec.appmakeup.infrastructure.persistence

import com.elitec.appmakeup.data.filesystem.ProjectSerializer
import com.elitec.appmakeup.domain.model.Project
import com.elitec.appmakeup.domain.model.ProjectLocation
import com.elitec.appmakeup.domain.model.ProjectRepository
import okio.FileSystem
import okio.Path.Companion.toPath

class FileProjectRepository(
    private val fileSystem: FileSystem,
    private val serializer: ProjectSerializer
) : ProjectRepository {

    companion object {
        const val PROJECT_FILE = "project.json"
    }

    override fun save(location: ProjectLocation, project: Project) {
        val root = location.value.toPath() / project.name
        fileSystem.createDirectories(root)

        val file = root / PROJECT_FILE
        val content = serializer.serialize(project)

        fileSystem.write(file) {
            writeUtf8(content)
        }
    }

    override fun load(location: ProjectLocation): Project? {
        val file = location.value.toPath() / PROJECT_FILE
        if (!fileSystem.exists(file)) return null

        val raw = fileSystem.read(file) {
            readUtf8()
        }

        return serializer.deserialize(raw)
    }
}