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

    private val tag = "[GenerationPipeline]---> "
    fun run(context: GenerationContext): GenerationResult {

        println("$tag 🚀 GenerationPipeline.run START for feature: ${context.feature.name}")

        val validation = validationStage.validate(context)
        println("$tag Validation of GenerationContext: $validation")

        if (!validation.isValid()) {
            return GenerationResult.Failure(
                (validation as ValidationResult.Invalid).reason
            )
        }

        val plan = planningStage.plan(context)
        println("$tag Plan of GenerationContext: $plan")

        val artifacts = generationStage.generate(context, plan)

        println("📦 Artifacts generated: ${artifacts.size}")

        val writeResult = writingStage.write(
            outputPath = context.outputPath,
            artifacts = artifacts,
            context = context
        )
        if (!writeResult.isSuccess()) return writeResult

        reportingStage.report(artifacts)

        return GenerationResult.Success
    }
}