package com.elitec.appmakeup.domain.template

data class DirectoryNode(
    val name: String,
    val children: List<DirectoryNode> = emptyList(),
    val files: List<FileNode> = emptyList()
)