package com.elitec.appmakeup.core.v4.generators.repository

import com.elitec.appmakeup.core.v4.contracts.GenerationContext
import com.elitec.appmakeup.core.v4.contracts.RepositoryContract
import com.elitec.appmakeup.core.v4.pipeline.GeneratedArtifact
import com.elitec.appmakeup.core.v4.pipeline.LayerGenerator
import com.elitec.appmakeup.core.v4.templates.repository.RepositoryTemplate

class RepositoryGenerator(
    private val contracts: List<RepositoryContract>,
    private val template: RepositoryTemplate = RepositoryTemplate()
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
                artifacts += generateRepository(featureName, contract)
            }

        return artifacts
    }

    private fun generateRepository(
        featureName: String,
        contract: RepositoryContract
    ): GeneratedArtifact {

        val entityName = contract.entity.name

        val packageName =
            "features.$featureName.domain.repository"

        val model = mapOf(
            "package" to packageName,
            "contract" to contract
        )

        val content = template.render(model)

        val path =
            "features/$featureName/domain/repository/${entityName}Repository.kt"

        return GeneratedArtifact(
            relativePath = path,
            content = content
        )
    }
}