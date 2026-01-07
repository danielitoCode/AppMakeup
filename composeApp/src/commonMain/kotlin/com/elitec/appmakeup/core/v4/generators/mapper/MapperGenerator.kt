package com.elitec.appmakeup.core.v4.generators.mapper

import com.elitec.appmakeup.core.v4.contracts.GenerationContext
import com.elitec.appmakeup.core.v4.contracts.MapperContract
import com.elitec.appmakeup.core.v4.pipeline.GeneratedArtifact
import com.elitec.appmakeup.core.v4.pipeline.LayerGenerator
import com.elitec.appmakeup.core.v4.templates.mapper.MapperTemplate

class MapperGenerator(
    private val contracts: List<MapperContract>,
    private val template: MapperTemplate = MapperTemplate()
) : LayerGenerator {

    override fun generate(
        context: GenerationContext
    ): List<GeneratedArtifact> {

        val featureName = context.feature.name.lowercase()
        val artifacts = mutableListOf<GeneratedArtifact>()

        contracts
            .filter { contract ->
                context.feature.entities.contains(contract.entity)
            }
            .forEach { contract ->
                artifacts += generateMapper(featureName, contract)
            }

        return artifacts
    }

    private fun generateMapper(
        featureName: String,
        contract: MapperContract
    ): GeneratedArtifact {

        val fromSimple = contract.from.substringAfterLast(".")
        val toSimple = contract.to.substringAfterLast(".")

        val packageName =
            "features.$featureName.data.mapper"

        val model = mapOf(
            "package" to packageName,
            "contract" to contract
        )

        val content = template.render(model)

        val path =
            "features/$featureName/data/mapper/${fromSimple}To${toSimple}.kt"

        return GeneratedArtifact(
            relativePath = path,
            content = content
        )
    }
}