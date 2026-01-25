package com.elitec.appmakeup.generation

interface CodeGenerator {

    fun generate(intent: CodeGenerationIntent,dryRun: Boolean)
}