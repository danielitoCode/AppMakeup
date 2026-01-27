package com.elitec.appmakeup.core.v5.generators

import com.elitec.appmakeup.core.v4.pipeline.GeneratedArtifact
import com.elitec.appmakeup.core.v5.domain.contracts.GenerationContext
import com.elitec.appmakeup.core.v5.pipeline.LayerGenerator

class CompositeLayerGenerator(
    private val generators: List<LayerGenerator>
) : LayerGenerator {

    override fun generate(
        context: GenerationContext
    ): List<GeneratedArtifact> =
        generators.flatMap { it.generate(context) }
}