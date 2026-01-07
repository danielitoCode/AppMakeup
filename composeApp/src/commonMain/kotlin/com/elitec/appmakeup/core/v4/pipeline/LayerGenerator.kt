package com.elitec.appmakeup.core.v4.pipeline

import com.elitec.appmakeup.core.v4.contracts.GenerationContext

interface LayerGenerator {
    fun generate(
        context: GenerationContext
    ): List<GeneratedArtifact>
}