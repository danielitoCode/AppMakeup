package com.elitec.appmakeup.core.v5.preview

import com.elitec.appmakeup.core.v4.validation.ValidationResult
import com.elitec.appmakeup.core.v5.domain.contracts.GenerationContext
import com.elitec.appmakeup.core.v5.pipeline.GenerationStage
import com.elitec.appmakeup.core.v5.pipeline.PlanningStage
import com.elitec.appmakeup.core.v5.pipeline.ValidationStage

class PreviewGenerationPipeline(
    private val validationStage: ValidationStage,
    private val planningStage: PlanningStage,
    private val generationStage: GenerationStage
) {

    fun run(context: GenerationContext): GenerationPreviewResult {

        val validation = validationStage.validate(context)
        require(validation.isValid()) {
            (validation as ValidationResult.Invalid).reason
        }

        val plan = planningStage.plan(context)
        val artifacts = generationStage.generate(context, plan)

        return GenerationPreviewResult(
            featureName = context.feature.name,
            plan = plan,
            files = artifacts.map { it.relativePath }
        )
    }
}