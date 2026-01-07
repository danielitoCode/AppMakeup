package com.elitec.appmakeup.projects.filesystem

import com.elitec.appmakeup.projects.templates.ProjectTemplateRenderer
import java.io.File

class FileRenderer(
    private val renderer: ProjectTemplateRenderer
) {
    fun renderFile(
        source: File,
        target: File,
        variables: Map<String, String>
    ) {
        val content = source.readText()
        val rendered = renderer.render(content, variables)
        target.writeText(rendered)
    }
}