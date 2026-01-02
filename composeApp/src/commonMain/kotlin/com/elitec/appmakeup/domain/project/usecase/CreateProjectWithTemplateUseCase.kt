package com.elitec.appmakeup.domain.project.usecase

import com.elitec.appmakeup.domain.project.Project
import com.elitec.appmakeup.domain.project.ProjectLocation
import com.elitec.appmakeup.domain.template.ProjectTemplate
import com.elitec.appmakeup.domain.template.StructureGenerationResult
import com.elitec.appmakeup.domain.template.usecase.GenerateProjectStructureFromTemplateUseCase

class CreateProjectWithTemplateUseCase(
    private val generateStructure: GenerateProjectStructureFromTemplateUseCase,
    private val saveProject: SaveProjectUseCase
) {

    fun execute(
        project: Project,
        location: ProjectLocation,
        template: ProjectTemplate
    ): StructureGenerationResult {

        // 1️⃣ generar estructura base
        val structureResult =
            generateStructure.execute(project, location, template)

        if (structureResult is StructureGenerationResult.Failure) {
            return structureResult
        }

        // 2️⃣ persistir proyecto (project.amk.json)
        saveProject.execute(project, location)

        return StructureGenerationResult.Success
    }
}