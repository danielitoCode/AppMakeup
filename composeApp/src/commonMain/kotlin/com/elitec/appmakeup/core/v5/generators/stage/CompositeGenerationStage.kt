package com.elitec.appmakeup.core.v5.generators.stage

import com.elitec.appmakeup.core.v4.pipeline.GeneratedArtifact
import com.elitec.appmakeup.core.v4.pipeline.GenerationPlan
import com.elitec.appmakeup.core.v5.domain.contracts.GenerationContext
import com.elitec.appmakeup.core.v5.generators.feature.FeatureGenerator
import com.elitec.appmakeup.core.v5.pipeline.GenerationStage

class CompositeGenerationStage(
    private val generators: List<FeatureGenerator>
) : GenerationStage {

    override fun generate(
        context: GenerationContext,
        plan: GenerationPlan
    ): List<GeneratedArtifact> {
        return generators.flatMap { it.generate(context, plan) }
    }
}