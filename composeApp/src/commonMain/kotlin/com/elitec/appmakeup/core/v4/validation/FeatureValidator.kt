package com.elitec.appmakeup.core.v4.validation

import com.elitec.appmakeup.core.v4.definition.CoreFeature
import com.elitec.appmakeup.core.v4.definition.CoreLayer

class FeatureValidator(
    private val entityValidator: EntityValidator = EntityValidator()
) : Validator<CoreFeature> {

    override fun validate(target: CoreFeature): ValidationResult {

        if (target.name.isBlank()) {
            return ValidationResult.Invalid("Feature name cannot be blank")
        }

        if (target.entities.isEmpty()) {
            return ValidationResult.Invalid("Feature ${target.name} must contain at least one entity")
        }

        if (target.layers.isEmpty()) {
            return ValidationResult.Invalid("Feature ${target.name} must declare at least one layer")
        }

        if (!target.layers.contains(CoreLayer.DOMAIN)) {
            return ValidationResult.Invalid(
                "Feature ${target.name} must include DOMAIN layer"
            )
        }

        target.entities.forEach {
            val result = entityValidator.validate(it)
            if (!result.isValid()) return result
        }

        return ValidationResult.Valid
    }
}