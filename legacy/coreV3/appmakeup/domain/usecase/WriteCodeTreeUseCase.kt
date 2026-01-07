package com.elitec.appmakeup.domain.usecase

import com.elitec.appmakeup.domain.codegen.CodeTree
import com.elitec.appmakeup.domain.codegen.CodeTreeWriter
import com.elitec.appmakeup.domain.model.ProjectLocation

class WriteCodeTreeUseCase(
    private val writer: CodeTreeWriter
) {
    fun execute(location: ProjectLocation, tree: CodeTree) {
        writer.write(location, tree)
    }
}
