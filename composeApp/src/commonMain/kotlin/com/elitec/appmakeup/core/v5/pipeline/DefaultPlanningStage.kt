package com.elitec.appmakeup.core.v5.pipeline


import com.elitec.appmakeup.core.v4.contracts.MapperContract
import com.elitec.appmakeup.core.v4.contracts.RepositoryContract
import com.elitec.appmakeup.core.v4.definition.CoreLayer
import com.elitec.appmakeup.core.v4.pipeline.GenerationPlan
import com.elitec.appmakeup.core.v5.domain.contracts.GenerationContext
import com.elitec.appmakeup.logs.Logger

class DefaultPlanningStage(
    private val repositoryContracts: List<RepositoryContract> = emptyList(),
    private val mapperContracts: List<MapperContract> = emptyList()
) : PlanningStage {

    private val tag = "[DefaultPlanningStage]"

    override fun plan(context: GenerationContext): GenerationPlan {

        val feature = context.feature
        val layers = feature.layers

        val generateDomain = CoreLayer.DOMAIN in layers
        val generateData = CoreLayer.DATA in layers
        val generatePresentation = CoreLayer.PRESENTATION in layers

        // ✅ Core V5: si hay entidades, hay repos + mappers
        val hasEntities = feature.entities.isNotEmpty()

        val generateRepositories = generateData && hasEntities
        val generateMappers = generateData && hasEntities

        Logger.success(
            tag,
            "Plan => domain=$generateDomain, data=$generateData, " +
                    "repo=$generateRepositories, mapper=$generateMappers"
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