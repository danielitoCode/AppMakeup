package com.elitec.appmakeup.core.v4.pipeline

data class GenerationPlan(
    val generateDomain: Boolean,
    val generateData: Boolean,
    val generatePresentation: Boolean,
    val generateRepositories: Boolean,
    val generateMappers: Boolean
)