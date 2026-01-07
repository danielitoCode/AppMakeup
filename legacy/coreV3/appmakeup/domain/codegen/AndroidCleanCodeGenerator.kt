package com.elitec.appmakeup.domain.codegen

import com.elitec.appmakeup.domain.model.Project

/**
 * CoreV3 generator: produces an in-memory [CodeTree] with a basic
 * Android Clean structure.
 *
 * Output layout:
 * <ProjectName>/
 *   src/
 *     <feature>/
 *       domain/
 *       data/
 *       presentation/
 */
class AndroidCleanCodeGenerator : CodeGenerator {

    override fun generate(project: Project): CodeTree {
        val featureDirs = project.features.map { feature ->
            CodeDirectory(
                name = feature.name,
                children = listOf(
                    CodeDirectory(name = "domain"),
                    CodeDirectory(name = "data"),
                    CodeDirectory(name = "presentation")
                )
            )
        }

        val root = CodeDirectory(
            name = project.name,
            children = listOf(
                CodeDirectory(
                    name = "src",
                    children = featureDirs
                )
            )
        )

        return CodeTree(directories = listOf(root))
    }
}
