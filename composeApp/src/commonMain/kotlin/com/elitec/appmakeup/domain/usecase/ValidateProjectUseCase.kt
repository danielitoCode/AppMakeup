package com.elitec.appmakeup.domain.usecase

import com.elitec.appmakeup.domain.project.Project
import com.elitec.appmakeup.domain.validation.ProjectRule
import com.elitec.appmakeup.domain.validation.RuleViolation

class ValidateProjectUseCase(
    private val rules: List<ProjectRule>
) {

    fun execute(project: Project): List<RuleViolation> =
        rules.flatMap { it.validate(project) }
}