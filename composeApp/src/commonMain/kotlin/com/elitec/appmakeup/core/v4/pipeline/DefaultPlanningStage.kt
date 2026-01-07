package com.elitec.appmakeup.core.v4.pipeline

import com.elitec.appmakeup.core.v4.contracts.GenerationContext
import com.elitec.appmakeup.core.v4.contracts.MapperContract
import com.elitec.appmakeup.core.v4.contracts.RepositoryContract
import com.elitec.appmakeup.core.v4.definition.CoreLayer

class DefaultPlanningStage(
    private val repositoryContracts: List<RepositoryContract> = emptyList(),
    private val mapperContracts: List<MapperContract> = emptyList()
) : PlanningStage {

    override fun plan(context: GenerationContext): GenerationPlan {

        val layers = context.feature.layers

        val generateDomain = layers.contains(CoreLayer.DOMAIN)

        val generateData = layers.contains(CoreLayer.DATA)

        val generatePresentation = layers.contains(CoreLayer.PRESENTATION)

        val generateRepositories =
            generateData && repositoryContracts.isNotEmpty()

        val generateMappers =
            generateData && mapperContracts.isNotEmpty()

        return GenerationPlan(
            generateDomain = generateDomain,
            generateData = generateData,
            generatePresentation = generatePresentation,
            generateRepositories = generateRepositories,
            generateMappers = generateMappers
        )
    }
}