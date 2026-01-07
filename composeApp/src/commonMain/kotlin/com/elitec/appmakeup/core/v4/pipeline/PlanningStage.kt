package com.elitec.appmakeup.core.v4.pipeline

import com.elitec.appmakeup.core.v4.contracts.GenerationContext

interface PlanningStage {
    fun plan(context: GenerationContext): GenerationPlan
}