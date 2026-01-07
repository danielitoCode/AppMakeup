package com.elitec.appmakeup.core.v4.pipeline

data class GenerationReport(
    val generatedFiles: Int,
    val skippedFiles: Int,
    val errors: List<String>
)