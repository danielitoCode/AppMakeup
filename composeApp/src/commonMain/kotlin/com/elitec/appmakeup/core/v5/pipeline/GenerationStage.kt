package com.elitec.appmakeup.core.v5.pipeline

import com.elitec.appmakeup.core.v4.pipeline.GeneratedArtifact
import com.elitec.appmakeup.core.v4.pipeline.GenerationPlan
import com.elitec.appmakeup.core.v5.domain.contracts.GenerationContext

interface GenerationStage {
    fun generate(
        context: GenerationContext,
        plan: GenerationPlan
    ): List<GeneratedArtifact>
}