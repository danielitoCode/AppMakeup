package com.elitec.appmakeup.core.v4.pipeline

import com.elitec.appmakeup.core.v4.contracts.GenerationContext

interface GenerationStage {
    fun generate(
        context: GenerationContext,
        plan: GenerationPlan
    ): List<GeneratedArtifact>
}