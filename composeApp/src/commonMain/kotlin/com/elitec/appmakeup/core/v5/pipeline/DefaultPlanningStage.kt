package com.elitec.appmakeup.core.v5.pipeline


import com.elitec.appmakeup.core.v4.contracts.RepositoryContract
import com.elitec.appmakeup.core.v4.pipeline.GenerationPlan
import com.elitec.appmakeup.core.v5.domain.contracts.GenerationContext

class DefaultPlanningStage(
    private val repositoryContracts: List<RepositoryContract>
) : PlanningStage {

    override fun plan(context: GenerationContext): GenerationPlan {

        val hasRepositories =
            repositoryContracts.any { it.entity in context.feature.entities }

        return GenerationPlan(
            generateDomain = true,
            generateData = true,
            generateRepositories = hasRepositories,
            generateMappers = hasRepositories,
            generatePresentation = false
        )
    }
}