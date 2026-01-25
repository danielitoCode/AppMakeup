package com.elitec.appmakeup.generation

import com.elitec.appmakeup.projects.model.AppMakeupProject

class GenerateCodeUseCase(
    private val codeGenerator: CodeGenerator
) {

    fun execute(project: AppMakeupProject, dryRun: Boolean) {

        require(project.features.isNotEmpty()) {
            "Project must have at least one feature to generate code"
        }

        val intent = CodeGenerationIntent(
            project = project,
            exportPath = project.exportPath
        )

        codeGenerator.generate(intent, dryRun)
    }
}