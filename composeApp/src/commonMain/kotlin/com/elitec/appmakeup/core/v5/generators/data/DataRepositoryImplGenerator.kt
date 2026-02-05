package com.elitec.appmakeup.core.v5.generators.data

import com.elitec.appmakeup.core.v4.pipeline.GeneratedArtifact
import com.elitec.appmakeup.core.v4.pipeline.GenerationPlan
import com.elitec.appmakeup.core.v5.domain.contracts.GenerationContext
import com.elitec.appmakeup.core.v5.generators.feature.FeatureGenerator


class DataRepositoryImplGenerator : FeatureGenerator {

    override fun generate(
        context: GenerationContext,
        plan: GenerationPlan
    ): List<GeneratedArtifact> {
        if (!plan.generateRepositories) return emptyList()

        val feature = context.feature

        return feature.entities.map { entity ->
            GeneratedArtifact(
                relativePath = "features/${feature.name.lowercase()}/data/repositories/${entity.name}RepositoryImpl.kt",
                content = """
                    package ${context.basePackage}.features.${feature.name.lowercase()}.data.repositories

                    import ${context.basePackage}.features.${feature.name.lowercase()}.domain.repositories.${entity.name}Repository
                    import ${context.basePackage}.features.${feature.name.lowercase()}.domain.entities.${entity.name}

                    class ${entity.name}RepositoryImpl : ${entity.name}Repository {

                        override fun create(entity: ${entity.name}) {}
                        override fun findById(id: String): ${entity.name}? = null
                        override fun update(entity: ${entity.name}) {}
                        override fun delete(id: String) {}
                    }
                """.trimIndent()
            )
        }
    }
}