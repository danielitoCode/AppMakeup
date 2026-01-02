package com.elitec.appmakeup.domain.codegen

import com.elitec.appmakeup.domain.project.Project
import com.elitec.appmakeup.domain.template.ProjectTemplate

interface CodeGenerator {
    fun generate(
        project: Project,
        template: ProjectTemplate
    ): GeneratedDirectory
}