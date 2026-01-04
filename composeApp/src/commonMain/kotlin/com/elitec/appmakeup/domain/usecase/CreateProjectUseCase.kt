package com.elitec.appmakeup.domain.usecase

import com.elitec.appmakeup.domain.model.Project
import com.elitec.appmakeup.domain.model.ProjectLocation

class CreateProjectUseCase {

    fun execute(project: Project): Project {
        // Core V3: solo devuelve el proyecto creado
        return project
    }
}