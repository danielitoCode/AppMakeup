package com.elitec.appmakeup.core.v4.pipeline

import com.elitec.appmakeup.core.v4.contracts.GenerationContext
import com.elitec.appmakeup.core.v4.contracts.GenerationResult
import com.elitec.appmakeup.core.v4.validation.ValidationResult

class GenerationPipeline(
    private val validationStage: ValidationStage,
    private val planningStage: PlanningStage,
    private val generationStage: GenerationStage,
    private val writingStage: WritingStage,
    private val reportingStage: ReportingStage
) {

    fun run(context: GenerationContext): GenerationResult {

        val validation = validationStage.validate(context)
        if (!validation.isValid()) {
            return GenerationResult.Failure(
                (validation as ValidationResult.Invalid).reason
            )
        }

        val plan = planningStage.plan(context)

        val artifacts = generationStage.generate(context, plan)

        val writeResult = writingStage.write(context.outputPath, artifacts)
        if (!writeResult.isSuccess()) return writeResult

        reportingStage.report(artifacts)

        return GenerationResult.Success
    }
}