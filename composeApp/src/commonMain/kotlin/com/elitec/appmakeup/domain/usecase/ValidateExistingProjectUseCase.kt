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

    fun execute(location: ProjectLocation): ProjectValidationResult {
        val path = location.value.toPath()

        if (!fileSystem.exists(path)) {
            return ProjectValidationResult.Invalid.DirectoryNotFound
        }

        val project = try {
            projectRepository.load(location)
        } catch (e: Exception) {
            return ProjectValidationResult.Invalid.InvalidProjectFile
        } ?: return ProjectValidationResult.Invalid.MissingProjectFile

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