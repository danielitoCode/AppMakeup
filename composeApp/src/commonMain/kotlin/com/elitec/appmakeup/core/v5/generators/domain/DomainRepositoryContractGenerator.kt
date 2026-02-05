package com.elitec.appmakeup.core.v5.generators.domain

import com.elitec.appmakeup.core.v4.pipeline.GeneratedArtifact
import com.elitec.appmakeup.core.v4.pipeline.GenerationPlan
import com.elitec.appmakeup.core.v5.domain.contracts.GenerationContext
import com.elitec.appmakeup.core.v5.generators.feature.FeatureGenerator

class DomainRepositoryContractGenerator : FeatureGenerator {

    override fun generate(
        context: GenerationContext,
        plan: GenerationPlan
    ): List<GeneratedArtifact> {
        if (!plan.generateRepositories) return emptyList()

        val feature = context.feature

        return feature.entities.map { entity ->
            GeneratedArtifact(
                relativePath = "features/${feature.name.lowercase()}/domain/repositories/${entity.name}Repository.kt",
                content = """
                    package ${context.basePackage}.features.${feature.name.lowercase()}.domain.repositories

                    import ${context.basePackage}.features.${feature.name.lowercase()}.domain.entities.${entity.name}

                    interface ${entity.name}Repository {
                        fun create(entity: ${entity.name})
                        fun findById(id: String): ${entity.name}?
                        fun update(entity: ${entity.name})
                        fun delete(id: String)
                    }
                """.trimIndent()
            )
        }
    }
}