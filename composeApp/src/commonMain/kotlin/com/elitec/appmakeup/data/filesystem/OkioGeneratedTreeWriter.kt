package com.elitec.appmakeup.data.filesystem

import com.elitec.appmakeup.domain.codegen.GeneratedDirectory
import com.elitec.appmakeup.domain.codegen.GeneratedFile
import com.elitec.appmakeup.domain.codegen.GeneratedTreeWriter
import com.elitec.appmakeup.domain.project.ProjectLocation
import com.elitec.appmakeup.domain.template.DirectoryNode
import com.elitec.appmakeup.domain.template.ProjectStructure
import okio.FileSystem
import okio.Path
import okio.Path.Companion.toPath

class OkioGeneratedTreeWriter(
    private val fileSystem: FileSystem
) : GeneratedTreeWriter {

    override fun write(
        location: ProjectLocation,
        structure: ProjectStructure
    ) {
        val root = location.value.toPath()

        structure.directories.forEach { dir ->
            writeDirectory(root, dir)
        }
    }

    private fun writeDirectory(
        base: Path,
        node: DirectoryNode
    ) {
        val current = base / node.name
        fileSystem.createDirectories(current)

        node.files.forEach { file ->
            val filePath = current / file.name
            if (!fileSystem.exists(filePath)) {
                fileSystem.write(filePath) { }
            }
        }

        node.children.forEach { child ->
            writeDirectory(current, child)
        }
    }
}