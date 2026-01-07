package com.elitec.appmakeup.core.v4.contracts

import com.elitec.appmakeup.core.v4.definition.CoreArchitecture
import com.elitec.appmakeup.core.v4.definition.CoreFeature

data class GenerationContext(
    val architecture: CoreArchitecture,
    val feature: CoreFeature,
    val outputPath: String,
    val options: Map<String, Any> = emptyMap()
)