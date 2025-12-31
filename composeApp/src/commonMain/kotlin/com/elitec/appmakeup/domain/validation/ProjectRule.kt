package com.elitec.appmakeup.domain.validation

import com.elitec.appmakeup.domain.project.Project

interface ProjectRule {
    fun validate(project: Project): List<RuleViolation>
}