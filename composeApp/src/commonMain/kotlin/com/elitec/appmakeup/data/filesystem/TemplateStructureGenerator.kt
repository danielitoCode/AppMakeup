package com.elitec.appmakeup.data.filesystem

import com.elitec.appmakeup.domain.project.Project
import com.elitec.appmakeup.domain.project.ProjectLocation
import com.elitec.appmakeup.domain.template.DirectoryNode
import com.elitec.appmakeup.domain.template.ProjectTemplate
import com.elitec.appmakeup.domain.template.StructureGenerationResult
import com.elitec.appmakeup.domain.template.StructureGenerator
import okio.FileSystem
import okio.Path.Companion.toPath

class TemplateStructureGenerator(
    private val fileSystem: FileSystem
) : StructureGenerator {

    override fun generate(
        project: Project,
        location: ProjectLocation,
        template: ProjectTemplate
    ): StructureGenerationResult {
        return try {
            val root = location.value.toPath()

            fileSystem.createDirectories(root)

            template.structure().directories.forEach { dir ->
                createDirectoryRecursive(
                    base = root,
                    node = dir,
                    project = project
                )
            }

            StructureGenerationResult.Success
        } catch (e: Exception) {
            StructureGenerationResult.Failure.IOError(e.message ?: "Unknown IO error")
        }
    }

    private fun createDirectoryRecursive(
        base: okio.Path,
        node: DirectoryNode,
        project: Project
    ) {
        val resolvedName = resolveName(node.name, project)
        val current = base / resolvedName

        fileSystem.createDirectories(current)

        node.files.forEach { file ->
            val filePath = current / resolveName(file.name, project)
            if (!fileSystem.exists(filePath)) {
                fileSystem.write(filePath) { }
            }
        }

        node.children.forEach { child ->
            createDirectoryRecursive(current, child, project)
        }
    }

    private fun resolveName(
        name: String,
        project: Project
    ): String =
        when (name) {
            "<feature>" -> project.features.firstOrNull()?.name ?: "feature"
            else -> name
        }
}