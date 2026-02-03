package com.elitec.appmakeup.presentation.util

import com.elitec.appmakeup.presentation.model.FileTreeNode

fun buildFileTree(paths: List<String>): FileTreeNode.Directory {
    val root = FileTreeNode.Directory("root")

    paths.forEach { path ->
        val parts = path.split("/", "\\")
        var current = root

        parts.forEachIndexed { index, part ->
            val isFile = index == parts.lastIndex

            if (isFile) {
                current.children += FileTreeNode.File(part)
            } else {
                val dir =
                    current.children
                        .filterIsInstance<FileTreeNode.Directory>()
                        .firstOrNull { it.name == part }
                        ?: FileTreeNode.Directory(part).also {
                            current.children += it
                        }

                current = dir
            }
        }
    }

    return root
}