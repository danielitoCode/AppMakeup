package com.elitec.appmakeup.presentation.mapper

import com.elitec.appmakeup.presentation.model.FileTreeNode

object FileTreeBuilder {

    fun build(paths: List<String>): FileTreeNode.Directory {
        val root = FileTreeNode.Directory("root")

        paths
            .filter { it.isNotBlank() }
            .map { it.replace("\\", "/") } // 🔑 CLAVE
            .forEach { path ->

                var current = root
                val parts = path.split("/").filter { it.isNotBlank() }

                parts.forEachIndexed { index, part ->
                    val isFile = index == parts.lastIndex

                    if (isFile) {
                        current.children.add(FileTreeNode.File(part))
                    } else {
                        val dir =
                            current.children
                                .filterIsInstance<FileTreeNode.Directory>()
                                .firstOrNull { it.name == part }
                                ?: FileTreeNode.Directory(part).also {
                                    current.children.add(it)
                                }

                        current = dir
                    }
                }
            }

        return root
    }
}