package com.elitec.appmakeup.domain.template.impl

import com.elitec.appmakeup.domain.project.Project
import com.elitec.appmakeup.domain.template.DirectoryNode
import com.elitec.appmakeup.domain.template.ProjectStructure
import com.elitec.appmakeup.domain.template.ProjectTemplate

class CleanTemplate : ProjectTemplate {

    override fun structure(project: Project): ProjectStructure {
        return ProjectStructure(
            directories = listOf(DirectoryNode(
                name = project.name,
                children = listOf(
                    DirectoryNode("domain"),
                    DirectoryNode("data"),
                    DirectoryNode("presentation")
                )
            )
            )
        )
    }
}