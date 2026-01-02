package com.elitec.appmakeup.domain.usecase

import com.elitec.appmakeup.domain.project.Project
import com.elitec.appmakeup.domain.project.ProjectLocation

class InitializeProjectUseCase(
    private val loadProject: LoadProjectUseCase
) {

    /**
     * Inicializa un proyecto existente dentro de un workspace.
     *
     * - Si existe el proyecto (json presente), lo carga
     * - Si no existe, falla explícitamente
     */
    fun execute(
        location: ProjectLocation,
        projectName: String
    ): Project {
        return loadProject.execute(
            location = location,
            projectName = projectName
        ) ?: error(
            "Project '$projectName' not found at ${location.value}"
        )
    }
}