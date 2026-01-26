package com.elitec.appmakeup.generation

import com.elitec.appmakeup.core.v4.contracts.GenerationContext
import com.elitec.appmakeup.core.v4.pipeline.GenerationPlan
import com.elitec.appmakeup.core.v4.pipeline.PlanningStage

class PreviewGenerationPlanUseCase(
    private val planningStage: PlanningStage
) {

    fun execute(context: GenerationContext): GenerationPlan =
        planningStage.plan(context)
}