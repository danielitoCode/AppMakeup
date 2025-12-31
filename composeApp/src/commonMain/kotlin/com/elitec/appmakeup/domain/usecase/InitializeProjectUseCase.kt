package com.elitec.appmakeup.domain.usecase

import com.elitec.appmakeup.domain.project.Project
import com.elitec.appmakeup.domain.project.ProjectLocation

class InitializeProjectUseCase(
    private val loadProject: LoadProjectUseCase
) {
    /**
     * Inicializa el proyecto en una ubicación:
     * - si existe (hay json), lo carga
     * - si no existe, crea uno nuevo con el factory provisto
     */
    fun execute(location: ProjectLocation): Project {
        return loadProject.execute(location)
            ?: error("Project not found at ${location.value}")
    }
}