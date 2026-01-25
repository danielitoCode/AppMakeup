package com.elitec.appmakeup.generation

import com.elitec.appmakeup.exports.ProjectExporter

class ProjectCodeGenerator(
    private val exporter: ProjectExporter
) : CodeGenerator {

    override fun generate(intent: CodeGenerationIntent, dryRun: Boolean) {
        exporter.export(intent.project, dryRun)
    }
}