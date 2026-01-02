package com.elitec.appmakeup.domain.usecase

import com.elitec.appmakeup.domain.project.CURRENT_PROJECT_VERSION
import com.elitec.appmakeup.domain.project.ProjectLocation
import com.elitec.appmakeup.domain.repository.ProjectRepository
import com.elitec.appmakeup.domain.validation.ProjectValidationResult
import okio.FileSystem
import okio.Path.Companion.toPath

class ValidateExistingProjectUseCase(
    private val projectRepository: ProjectRepository,
    private val fileSystem: FileSystem
) {

    fun execute(
        location: ProjectLocation,
        projectName: String
    ): ProjectValidationResult {

        val projectDir = location.value.toPath() / projectName

        // 1️⃣ Workspace existe
        if (!fileSystem.exists(location.value.toPath())) {
            return ProjectValidationResult.Invalid.DirectoryNotFound
        }

        // 2️⃣ Proyecto existe
        if (!fileSystem.exists(projectDir)) {
            return ProjectValidationResult.Invalid.MissingProjectFile
        }

        // 3️⃣ Intentar cargar proyecto
        val project = try {
            projectRepository.load(
                location = location,
                projectName = projectName
            )
        } catch (e: Exception) {
            return ProjectValidationResult.Invalid.InvalidProjectFile
        } ?: return ProjectValidationResult.Invalid.MissingProjectFile

        // 4️⃣ Validar versión
        return when {
            project.version > CURRENT_PROJECT_VERSION ->
                ProjectValidationResult.Invalid.UnsupportedVersion(
                    projectVersion = project.version,
                    supportedVersion = CURRENT_PROJECT_VERSION
                )

            project.version < CURRENT_PROJECT_VERSION ->
                ProjectValidationResult.Invalid.MigratableVersion(
                    projectVersion = project.version,
                    supportedVersion = CURRENT_PROJECT_VERSION
                )

            else -> ProjectValidationResult.Valid
        }
    }
}