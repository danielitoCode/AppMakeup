package com.elitec.appmakeup.domain.template.usecase

import com.elitec.appmakeup.domain.project.Project
import com.elitec.appmakeup.domain.project.ProjectLocation
import com.elitec.appmakeup.domain.template.ProjectTemplate
import com.elitec.appmakeup.domain.template.StructureGenerationResult
import com.elitec.appmakeup.domain.template.StructureGenerator

class GenerateProjectStructureFromTemplateUseCase(
    private val generator: StructureGenerator
) {
    fun execute(
        project: Project,
        location: ProjectLocation,
        template: ProjectTemplate
    ): StructureGenerationResult =
        generator.generate(project, location, template)
}