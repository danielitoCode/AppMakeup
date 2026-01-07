package com.elitec.appmakeup.generation

import com.elitec.appmakeup.exports.ProjectExporter

class ProjectCodeGenerator(
    private val exporter: ProjectExporter
) : CodeGenerator {

    override fun generate(intent: CodeGenerationIntent) {
        exporter.export(intent.project)
    }
}