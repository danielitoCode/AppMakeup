package com.elitec.appmakeup.core.v5.validation

import com.elitec.appmakeup.core.v5.validation.ArchitectureValidator
import com.elitec.appmakeup.core.v4.validation.FeatureValidator
import com.elitec.appmakeup.core.v4.validation.ValidationResult
import com.elitec.appmakeup.core.v5.domain.contracts.GenerationContext
import com.elitec.appmakeup.core.v5.pipeline.ValidationStage

class DefaultValidationStage(
    private val featureValidator: FeatureValidator,
    private val architectureValidator: ArchitectureValidator
) : ValidationStage {

    override fun validate(context: GenerationContext): ValidationResult {

        // 1️⃣ Validar feature (incluye entidades)
        val featureResult = featureValidator.validate(context.feature)
        if (!featureResult.isValid()) return featureResult

        // 2️⃣ Validar arquitectura vs feature
        val archResult = architectureValidator.validate(
            context.architecture to context.feature
        )
        if (!archResult.isValid()) return archResult

        // 3️⃣ Output path básico
        if (context.outputPath.isBlank()) {
            return ValidationResult.Invalid("Output path cannot be blank")
        }

        return ValidationResult.Valid
    }
}