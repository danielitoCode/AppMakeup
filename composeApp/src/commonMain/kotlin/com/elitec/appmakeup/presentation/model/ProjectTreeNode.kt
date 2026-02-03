package com.elitec.appmakeup.presentation.model

sealed class ProjectTreeNode(
    open val id: String,
    open val name: String
) {
    data class Directory(
        override val id: String,
        override val name: String,
        val children: List<ProjectTreeNode>
    ) : ProjectTreeNode(id, name)

    data class File(
        override val id: String,
        override val name: String
    ) : ProjectTreeNode(id, name)
}