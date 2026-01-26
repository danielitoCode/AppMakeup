package com.elitec.appmakeup.generation

import com.elitec.appmakeup.core.v4.contracts.GenerationResult
import com.elitec.appmakeup.projects.model.AppMakeupProject

interface CodeGenerator {

    fun generate(intent: CodeGenerationIntent,dryRun: Boolean): GenerationResult
    fun preview(project: AppMakeupProject): List<String>
}