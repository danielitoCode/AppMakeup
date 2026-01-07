package com.elitec.appmakeup.domain.usecase

import com.elitec.appmakeup.domain.codegen.CodeGenerator
import com.elitec.appmakeup.domain.codegen.CodeTree
import com.elitec.appmakeup.domain.model.Project

class GenerateCodeTreeUseCase(
    private val generator: CodeGenerator
) {
    fun execute(project: Project): CodeTree = generator.generate(project)
}
