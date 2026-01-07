package com.elitec.appmakeup.data.filesystem

import com.elitec.appmakeup.domain.codegen.CodeDirectory
import com.elitec.appmakeup.domain.codegen.CodeFile
import com.elitec.appmakeup.domain.codegen.CodeTree
import com.elitec.appmakeup.domain.codegen.CodeTreeWriter
import com.elitec.appmakeup.domain.model.ProjectLocation
import okio.FileSystem
import okio.Path
import okio.Path.Companion.toPath

class OkioCodeTreeWriter(
    private val fileSystem: FileSystem
) : CodeTreeWriter {

    override fun write(location: ProjectLocation, tree: CodeTree) {
        val root = location.value.toPath()
        fileSystem.createDirectories(root)

        tree.directories.forEach { dir ->
            writeDirectory(root, dir)
        }
    }

    private fun writeDirectory(base: Path, dir: CodeDirectory) {
        val current = base / dir.name
        fileSystem.createDirectories(current)

        dir.files.forEach { file ->
            writeFile(current, file)
        }

        dir.children.forEach { child ->
            writeDirectory(current, child)
        }
    }

    private fun writeFile(base: Path, file: CodeFile) {
        val filePath = base / file.name
        fileSystem.createDirectories(filePath.parent!!)
        fileSystem.write(filePath) {
            writeUtf8(file.content)
        }
    }
}
