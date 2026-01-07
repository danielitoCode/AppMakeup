package com.elitec.appmakeup.core.v4.generators.domain

import com.elitec.appmakeup.core.v4.contracts.GenerationContext
import com.elitec.appmakeup.core.v4.definition.CoreEntity
import com.elitec.appmakeup.core.v4.pipeline.GeneratedArtifact
import com.elitec.appmakeup.core.v4.pipeline.LayerGenerator
import com.elitec.appmakeup.core.v4.templates.domain.DomainEntityTemplate

class DomainEntityGenerator(
    private val template: DomainEntityTemplate = DomainEntityTemplate()
) : LayerGenerator {

    override fun generate(
        context: GenerationContext
    ): List<GeneratedArtifact> {

        val featureName = context.feature.name.lowercase()
        val artifacts = mutableListOf<GeneratedArtifact>()

        context.feature.entities.forEach { entity ->
            artifacts += generateEntity(featureName, entity)
        }

        return artifacts
    }

    private fun generateEntity(
        featureName: String,
        entity: CoreEntity
    ): GeneratedArtifact {

        val packageName =
            "features.$featureName.domain.entities"

        val model = mapOf(
            "package" to packageName,
            "entity" to entity
        )

        val content = template.render(model)

        val path =
            "features/$featureName/domain/entities/${entity.name}.kt"

        return GeneratedArtifact(
            relativePath = path,
            content = content
        )
    }
}