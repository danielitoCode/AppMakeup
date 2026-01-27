package com.elitec.appmakeup.core.v5.pipeline

import com.elitec.appmakeup.core.v4.pipeline.GeneratedArtifact
import com.elitec.appmakeup.core.v5.domain.contracts.GenerationContext

interface LayerGenerator {
    fun generate(
        context: GenerationContext
    ): List<GeneratedArtifact>
}