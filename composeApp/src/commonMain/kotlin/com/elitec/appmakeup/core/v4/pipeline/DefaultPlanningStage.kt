package com.elitec.appmakeup.core.v4.pipeline

import com.elitec.appmakeup.core.v4.contracts.GenerationContext
import com.elitec.appmakeup.core.v4.contracts.MapperContract
import com.elitec.appmakeup.core.v4.contracts.RepositoryContract
import com.elitec.appmakeup.core.v4.definition.CoreLayer

class DefaultPlanningStage(
    private val repositoryContracts: List<RepositoryContract> = emptyList(),
    private val mapperContracts: List<MapperContract> = emptyList()
) : PlanningStage {

    private val tag = "[DefaultPlanningStage]---> "
    override fun plan(context: GenerationContext): GenerationPlan {

        println("$tag GenerationContext received: $context")

        val layers = context.feature.layers
        println("$tag Layers: $layers")

        val generateDomain = layers.contains(CoreLayer.DOMAIN)
        println("$tag Generate domain: $generateDomain")

        val generateData = layers.contains(CoreLayer.DATA)
        println("$tag Generate data: $generateData")

        val generatePresentation = layers.contains(CoreLayer.PRESENTATION)
        println("$tag Generate presentation: $generatePresentation")

        val generateRepositories =
            generateData && repositoryContracts.isNotEmpty()
        println("$tag Generate repositories: $generateRepositories")

        val generateMappers =
            generateData && mapperContracts.isNotEmpty()
        println("$tag Generate mappers: $generateMappers")

        println(
            "🧠 Plan => domain=$generateDomain, " +
                    "data=$generateData, " +
                    "repo=$generateRepositories, " +
                    "mapper=$generateMappers"
        )

        return GenerationPlan(
            generateDomain = generateDomain,
            generateData = generateData,
            generatePresentation = generatePresentation,
            generateRepositories = generateRepositories,
            generateMappers = generateMappers
        )
    }
}