package com.elitec.appmakeup.data.filesystem

import com.elitec.appmakeup.domain.codegen.GeneratedTreeWriter
import com.elitec.appmakeup.domain.project.Project
import com.elitec.appmakeup.domain.project.ProjectLocation
import com.elitec.appmakeup.domain.template.DirectoryNode
import com.elitec.appmakeup.domain.template.ProjectTemplate
import com.elitec.appmakeup.domain.template.StructureGenerationResult
import com.elitec.appmakeup.domain.template.StructureGenerator
import okio.FileSystem
import okio.Path.Companion.toPath

class TemplateStructureGenerator(
    private val writer: GeneratedTreeWriter
) : StructureGenerator {

    override fun generate(
        project: Project,
        location: ProjectLocation,
        template: ProjectTemplate
    ): StructureGenerationResult = try {

        val tree = template.structure(project)
        writer.write(location, tree)

        StructureGenerationResult.Success
    } catch (e: Exception) {
        StructureGenerationResult.Failure.IOError(
            e.message ?: "Unknown IO error"
        )
    }
}