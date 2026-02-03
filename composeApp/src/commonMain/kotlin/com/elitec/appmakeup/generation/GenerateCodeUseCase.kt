package com.elitec.appmakeup.generation

import com.elitec.appmakeup.core.v4.contracts.GenerationResult
import com.elitec.appmakeup.logs.Logger
import com.elitec.appmakeup.projects.model.AppMakeupProject

class GenerateCodeUseCase(
    private val codeGenerator: CodeGenerator
) {
    private val tag = "[GenerateCodeUseCase]"
    fun execute(project: AppMakeupProject, dryRun: Boolean): GenerationResult {

        Logger.success(tag, "Init executing Generation intent")

        require(project.features.isNotEmpty()) {
            "Project must have at least one feature to generate code"
        }

        val intent = CodeGenerationIntent(
            project = project,
            exportPath = "${project.path}/export"
        )

        Logger.success(tag, "make generation intent: $intent")
        return codeGenerator.generate(intent, dryRun)
    }

    fun preview(project: AppMakeupProject): List<String> {
        val result = codeGenerator.preview(project)

        Logger.success(tag, "[PREVIEW] Files:")
        result.forEach { println(" - $it") }

        return result
    }
}