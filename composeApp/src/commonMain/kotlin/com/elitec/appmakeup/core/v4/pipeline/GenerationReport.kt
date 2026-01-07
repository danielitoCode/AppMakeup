package com.elitec.appmakeup.core.v4.pipeline

data class GenerationReport(
    val generatedFiles: Int,
    val files: List<String>,
    val errors: List<String> = emptyList()
)