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

    companion object {
        const val PROJECT_FILE_NAME = "project.amk.json"
    }

    override fun load(
        location: ProjectLocation,
        projectName: String
    ): Project? {
        val projectDir = location.value.toPath() / projectName
        val projectFile = projectDir / PROJECT_FILE_NAME

        if (!fileSystem.exists(projectFile)) return null

        return fileSystem.read(projectFile) {
            json.decodeFromString<Project>(readUtf8())
        }
    }

    override fun save(
        location: ProjectLocation,
        project: Project
    ) {
        val projectDir = location.value.toPath() / project.name
        fileSystem.createDirectories(projectDir)

        val projectFile = projectDir / PROJECT_FILE_NAME
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