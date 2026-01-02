package com.elitec.appmakeup.domain.template.impl

import com.elitec.appmakeup.domain.template.DirectoryNode
import com.elitec.appmakeup.domain.template.ProjectStructure
import com.elitec.appmakeup.domain.template.ProjectTemplate

class CleanTemplate : ProjectTemplate {

    override val id: String = "clean"
    override val displayName: String = "Clean Architecture"
    override val description: String =
        "Feature-based Clean Architecture with domain, data and presentation layers"

    override fun structure(): ProjectStructure =
        ProjectStructure(
            directories = listOf(
                DirectoryNode(
                    name = "features",
                    children = listOf(
                        DirectoryNode(
                            name = "<feature>",
                            children = listOf(
                                DirectoryNode("domain"),
                                DirectoryNode("data"),
                                DirectoryNode("presentation")
                            )
                        )
                    )
                )
            )
        )
}