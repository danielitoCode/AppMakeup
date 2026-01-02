package com.elitec.appmakeup.domain.android

import com.elitec.appmakeup.domain.codegen.CodeGenerator
import com.elitec.appmakeup.domain.codegen.GeneratedDirectory
import com.elitec.appmakeup.domain.codegen.GeneratedFile
import com.elitec.appmakeup.domain.project.Project
import com.elitec.appmakeup.domain.template.ProjectTemplate

class AndroidCleanCodeGenerator : CodeGenerator {

    override fun generate(
        project: Project,
        template: ProjectTemplate
    ): GeneratedDirectory {

        return GeneratedDirectory(
            name = project.name,
            children = listOf(
                generateSrc(project)
            )
        )
    }

    private fun generateSrc(project: Project): GeneratedDirectory =
        GeneratedDirectory(
            name = "src",
            children = project.features.map { feature ->
                generateFeature(feature.name, feature.entity.name)
            }
        )

    private fun generateFeature(
        featureName: String,
        entityName: String
    ): GeneratedDirectory =
        GeneratedDirectory(
            name = featureName,
            children = listOf(
                generateDomain(featureName, entityName),
                generateData(featureName, entityName),
                generatePresentation(featureName, entityName)
            )
        )
}
