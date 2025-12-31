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

    override fun load(location: ProjectLocation): Project? {
        val projectRoot = location.value.toPath()
        val projectFile = projectRoot / projectFileName

        if (!fileSystem.exists(projectFile)) return null

        return fileSystem.read(projectFile) {
            json.decodeFromString<Project>(readUtf8())
        }
    }

    override fun save(location: ProjectLocation, project: Project) {
        println("save project in ${location.value}")

        val projectRoot = location.value.toPath()

        // 🔴 ESTA LÍNEA ES CRÍTICA
        fileSystem.createDirectories(projectRoot)

        val projectFile = projectRoot / projectFileName

        fileSystem.write(projectFile) {
            writeUtf8(json.encodeToString(project))
        }
    }

    override fun generateStructure(location: ProjectLocation, project: Project) {
        val projectRoot = location.value.toPath()
        fileSystem.createDirectories(projectRoot)

        val featuresDir = projectRoot / "features"
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