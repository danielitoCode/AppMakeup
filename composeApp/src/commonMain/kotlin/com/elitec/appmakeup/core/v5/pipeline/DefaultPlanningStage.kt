package com.elitec.appmakeup.core.v5.pipeline


import com.elitec.appmakeup.core.v4.definition.CoreLayer
import com.elitec.appmakeup.core.v4.pipeline.GenerationPlan
import com.elitec.appmakeup.core.v5.domain.contracts.GenerationContext
import com.elitec.appmakeup.logs.Logger

class DefaultPlanningStage : PlanningStage {

    private val tag = "[DefaultPlanningStage]---> "

    override fun plan(context: GenerationContext): GenerationPlan {

        Logger.success(tag, "GenerationContext received")

        val layers = context.feature.layers
        val contracts = context.feature.repositoryContracts

        val generateDomain = layers.contains(CoreLayer.DOMAIN)
        val generateData = layers.contains(CoreLayer.DATA)
        val generatePresentation = layers.contains(CoreLayer.PRESENTATION)

        val generateRepositories =
            generateData && contracts.isNotEmpty()

        val generateMappers =
            generateData && contracts.any {
                it.supportsRead || it.supportsCreate
            }

        Logger.success(
            tag,
            "Plan => domain=$generateDomain, " +
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