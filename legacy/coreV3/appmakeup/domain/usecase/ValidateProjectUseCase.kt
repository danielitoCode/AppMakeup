package com.elitec.appmakeup.domain.usecase

import com.elitec.appmakeup.domain.model.Project

class ValidateProjectUseCase {

    fun execute(project: Project): List<String> {
        val errors = mutableListOf<String>()

        if (project.name.isBlank()) {
            errors += "Project name cannot be empty"
        }

        project.features.forEach { feature ->
            if (feature.name.isBlank()) {
                errors += "Feature name cannot be empty"
            }
        }

        return errors
    }
}