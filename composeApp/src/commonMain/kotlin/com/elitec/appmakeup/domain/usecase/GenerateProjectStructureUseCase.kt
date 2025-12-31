package com.elitec.appmakeup.domain.usecase

import com.elitec.appmakeup.domain.project.Project
import com.elitec.appmakeup.domain.project.ProjectLocation
import com.elitec.appmakeup.domain.repository.ProjectRepository
import com.elitec.appmakeup.domain.validation.ProjectRule
import com.elitec.appmakeup.domain.validation.RuleViolation

class GenerateProjectStructureUseCase(
    private val repository: ProjectRepository
) {
    fun execute(
        location: ProjectLocation,
        project: Project
    ) {
        repository.generateStructure(location, project)
    }
}