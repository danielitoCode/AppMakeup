package com.elitec.appmakeup.presentation.model

import androidx.compose.ui.Modifier
import io.github.vooft.compose.treeview.core.tree.Tree

sealed class FileTreeNode {
    data class Directory(
        val name: String,
        val children: MutableList<FileTreeNode> = mutableListOf(),
        var expanded: Boolean = false
    ) : FileTreeNode()

    data class File(
        val name: String
    ) : FileTreeNode()
}
