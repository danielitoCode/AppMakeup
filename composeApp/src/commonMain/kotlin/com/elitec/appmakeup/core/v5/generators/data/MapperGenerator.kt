package com.elitec.appmakeup.core.v5.generators.data

import com.elitec.appmakeup.core.v4.pipeline.GeneratedArtifact
import com.elitec.appmakeup.core.v4.pipeline.GenerationPlan
import com.elitec.appmakeup.core.v5.domain.contracts.GenerationContext
import com.elitec.appmakeup.core.v5.generators.feature.FeatureGenerator

class MapperGenerator : FeatureGenerator {

    override fun generate(
        context: GenerationContext,
        plan: GenerationPlan
    ): List<GeneratedArtifact> {
        if (!plan.generateMappers) return emptyList()

        val feature = context.feature

        return feature.entities.map { entity ->
            GeneratedArtifact(
                relativePath = "features/${feature.name.lowercase()}/data/mappers/${entity.name}Mapper.kt",
                content = """
                    package ${context.basePackage}.features.${feature.name.lowercase()}.data.mappers

                    import ${context.basePackage}.features.${feature.name.lowercase()}.data.dto.${entity.name}Dto
                    import ${context.basePackage}.features.${feature.name.lowercase()}.domain.entities.${entity.name}

                    fun ${entity.name}Dto.toDomain() = ${entity.name}(
                        ${entity.properties.joinToString(", ") { "${it.name} = ${it.name}" }}
                    )

                    fun ${entity.name}.toDto() = ${entity.name}Dto(
                        ${entity.properties.joinToString(", ") { "${it.name} = ${it.name}" }}
                    )
                """.trimIndent()
            )
        }
    }
}