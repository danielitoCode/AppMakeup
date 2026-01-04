package com.elitec.appmakeup.domain.usecase

import com.elitec.appmakeup.domain.model.ProjectLocation
import com.elitec.appmakeup.domain.model.Project

/**
 * High-level CoreV3 use case.
 *
 * 1) generate CodeTree in-memory
 * 2) write it to disk
 */
class ScaffoldProjectUseCase(
    private val generateCodeTree: GenerateCodeTreeUseCase,
    private val writeCodeTree: WriteCodeTreeUseCase
) {
    fun execute(workspace: ProjectLocation, project: Project) {
        val tree = generateCodeTree.execute(project)
        writeCodeTree.execute(workspace, tree)
    }
}
