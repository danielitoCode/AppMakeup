package com.elitec.appmakeup.core.v4.generators.data

import com.elitec.appmakeup.core.v4.contracts.GenerationContext
import com.elitec.appmakeup.core.v4.contracts.RepositoryContract
import com.elitec.appmakeup.core.v4.pipeline.GeneratedArtifact
import com.elitec.appmakeup.core.v4.pipeline.LayerGenerator
import com.elitec.appmakeup.core.v4.templates.data.RepositoryImplTemplate

class RepositoryImplGenerator(
    private val contracts: List<RepositoryContract>,
    private val template: RepositoryImplTemplate = RepositoryImplTemplate()
) : LayerGenerator {

    override fun generate(
        context: GenerationContext
    ): List<GeneratedArtifact> {

        val featureName = context.feature.name.lowercase()
        val artifacts = mutableListOf<GeneratedArtifact>()

        contracts
            .filter { context.feature.entities.contains(it.entity) }
            .forEach { contract ->
                artifacts += generateImpl(featureName, contract)
            }

        return artifacts
    }

    private fun generateImpl(
        featureName: String,
        contract: RepositoryContract
    ): GeneratedArtifact {

        val entityName = contract.entity.name

        val packageName =
            "features.$featureName.data.repository"

        val model = mapOf(
            "package" to packageName,
            "contract" to contract
        )

        val content = template.render(model)

        val path =
            "features/$featureName/data/repository/${entityName}RepositoryImpl.kt"

        return GeneratedArtifact(
            relativePath = path,
            content = content
        )
    }
}