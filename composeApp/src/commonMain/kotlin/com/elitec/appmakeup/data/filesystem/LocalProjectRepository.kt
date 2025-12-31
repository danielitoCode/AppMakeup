package com.elitec.appmakeup.data.filesystem

import com.elitec.appmakeup.domain.project.Project
import com.elitec.appmakeup.domain.project.ProjectLocation
import com.elitec.appmakeup.domain.repository.ProjectRepository
import kotlinx.serialization.json.Json
import okio.FileSystem
import okio.Path.Companion.toPath

class LocalProjectRepository(
    private val fileSystem: FileSystem,
    private val json: Json
) : ProjectRepository {

    private val projectFileName = "project.amk.json"

    override fun load(
        location: ProjectLocation,
        projectId: String
    ): Project? {
        val rootPath = location.value.toPath()
        val projectFile = rootPath / projectId / projectFileName

        if (!fileSystem.exists(projectFile)) return null

        return fileSystem.read(projectFile) {
            json.decodeFromString<Project>(readUtf8())
        }
    }

    override fun save(
        location: ProjectLocation,
        project: Project
    ) {
        val rootPath = location.value.toPath()
        val projectDir = rootPath / project.name

        fileSystem.createDirectories(projectDir)

        val projectFile = projectDir / projectFileName
        fileSystem.write(projectFile) {
            writeUtf8(json.encodeToString(project))
        }
    }

    override fun generateStructure(
        location: ProjectLocation,
        project: Project
    ) {
        val rootPath = location.value.toPath()
        val projectDir = rootPath / project.name

        fileSystem.createDirectories(projectDir)

        val featuresDir = projectDir / "features"
        fileSystem.createDirectories(featuresDir)

        project.features.forEach { feature ->
            val featureDir = featuresDir / feature.name
            fileSystem.createDirectories(featureDir)

            fileSystem.createDirectories(featureDir / "domain")
            fileSystem.createDirectories(featureDir / "data")
            fileSystem.createDirectories(featureDir / "presentation")
        }
    }

    override fun export(
        location: ProjectLocation,
        project: Project
    ) {
        // V2
    }
}