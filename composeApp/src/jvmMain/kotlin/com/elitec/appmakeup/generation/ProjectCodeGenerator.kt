package com.elitec.appmakeup.generation

import com.elitec.appmakeup.core.v4.contracts.GenerationResult
import com.elitec.appmakeup.exports.ProjectExporter
import com.elitec.appmakeup.projects.model.AppMakeupProject

class ProjectCodeGenerator(
    private val exporter: ProjectExporter
) : CodeGenerator {

    private val tag = "[ProjectCodeGenerator]---> "

    override fun generate(intent: CodeGenerationIntent, dryRun: Boolean): GenerationResult {
        println("$tag Init executing Generation in platform with \ndryRun: $dryRun")
        return exporter.export(intent.project, dryRun)
    }
}