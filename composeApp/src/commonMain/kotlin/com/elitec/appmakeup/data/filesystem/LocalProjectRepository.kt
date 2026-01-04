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

    companion object {
        private const val PROJECT_FILE = "project.json"
    }

    override fun save(
        location: ProjectLocation,
        project: Project
    ) {
        // workspace/<project.name>/
        val projectRoot = location.value.toPath() / project.name
        fileSystem.createDirectories(projectRoot)

        val projectFile = projectRoot / PROJECT_FILE
        val content = serializer.serialize(project)

        fileSystem.write(projectFile) {
            writeUtf8(content)
        }
    }

    override fun load(
        location: ProjectLocation
    ): Project? {
        // location apunta al ROOT del proyecto
        val projectFile = location.value.toPath() / PROJECT_FILE

        if (!fileSystem.exists(projectFile)) return null

        return try {
            val content = fileSystem.read(projectFile) {
                readUtf8()
            }
            serializer.deserialize(content)
        } catch (e: Exception) {
            null
        }
    }
}