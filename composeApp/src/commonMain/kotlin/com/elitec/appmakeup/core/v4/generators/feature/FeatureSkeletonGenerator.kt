package com.elitec.appmakeup.core.v4.generators.feature

import com.elitec.appmakeup.core.v4.contracts.GenerationContext
import com.elitec.appmakeup.core.v4.definition.CoreLayer
import com.elitec.appmakeup.core.v4.pipeline.GeneratedArtifact
import com.elitec.appmakeup.core.v4.pipeline.LayerGenerator
import com.elitec.appmakeup.core.v4.templates.feature.FeatureLayerTemplate
import kotlin.collections.plusAssign

class FeatureSkeletonGenerator(
    private val template: FeatureLayerTemplate = FeatureLayerTemplate()
) : LayerGenerator {

    override fun generate(
        context: GenerationContext
    ): List<GeneratedArtifact> {

        val featureName = context.feature.name.lowercase()
        val artifacts = mutableListOf<GeneratedArtifact>()

        context.feature.layers.forEach { layer ->
            artifacts += generateLayer(featureName, layer)
        }

        return artifacts
    }

    private fun generateLayer(
        featureName: String,
        layer: CoreLayer
    ): GeneratedArtifact {

        val layerName = layer.name.lowercase()

        val path =
            "features/$featureName/$layerName/.keep"

        val content = template.render(
            model = mapOf(
                "feature" to featureName,
                "layer" to layer
            )
        )

        return GeneratedArtifact(
            relativePath = path,
            content = content
        )
    }
}