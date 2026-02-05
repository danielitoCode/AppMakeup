package com.elitec.appmakeup.core.v5.generators.data

import com.elitec.appmakeup.core.v4.pipeline.GeneratedArtifact
import com.elitec.appmakeup.core.v4.pipeline.GenerationPlan
import com.elitec.appmakeup.core.v5.domain.contracts.GenerationContext
import com.elitec.appmakeup.core.v5.generators.feature.FeatureGenerator

class DtoGenerator : FeatureGenerator {

    override fun generate(
        context: GenerationContext,
        plan: GenerationPlan
    ): List<GeneratedArtifact> {
        if (!plan.generateData) return emptyList()

        val feature = context.feature

        return feature.entities.map { entity ->
            GeneratedArtifact(
                relativePath = "features/${feature.name.lowercase()}/data/dto/${entity.name}Dto.kt",
                content = """
                    package ${context.basePackage}.features.${feature.name.lowercase()}.data.dto

                    data class ${entity.name}Dto(
                        ${entity.properties.joinToString(",\n") {
                    "val ${it.name}: ${it.type}"
                }}
                    )
                """.trimIndent()
            )
        }
    }
}