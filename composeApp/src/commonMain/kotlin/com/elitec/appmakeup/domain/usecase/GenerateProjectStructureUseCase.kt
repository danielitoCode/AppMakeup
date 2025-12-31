package com.elitec.appmakeup.domain.usecase

import com.elitec.appmakeup.domain.project.Project
import com.elitec.appmakeup.domain.repository.ProjectRepository
import com.elitec.appmakeup.domain.validation.ProjectRule
import com.elitec.appmakeup.domain.validation.RuleViolation

class GenerateProjectStructureUseCase(
    private val repository: ProjectRepository,
    private val rules: List<ProjectRule>
) {

    fun execute(project: Project): List<RuleViolation> {
        val violations = rules.flatMap { it.validate(project) }
        if (violations.isNotEmpty()) return violations

        repository.generateStructure(project)
        return emptyList()
    }
}