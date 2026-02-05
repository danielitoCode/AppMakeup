package com.elitec.appmakeup.core.v5.domain.contracts

import com.elitec.appmakeup.core.v4.definition.CoreArchitecture
import com.elitec.appmakeup.core.v5.definitions.CoreFeature

data class GenerationContext(
    val architecture: CoreArchitecture,
    val feature: CoreFeature,
    val outputPath: String,
    val basePackage: String,
    val options: Map<String, Any> = emptyMap()
)