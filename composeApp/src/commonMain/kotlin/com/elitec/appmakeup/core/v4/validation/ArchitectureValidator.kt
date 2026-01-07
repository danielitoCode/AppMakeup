package com.elitec.appmakeup.core.v4.validation

import com.elitec.appmakeup.core.v4.definition.CoreArchitecture
import com.elitec.appmakeup.core.v4.definition.CoreLayer

class ArchitectureValidator : Validator<CoreArchitecture> {

    override fun validate(target: CoreArchitecture): ValidationResult {

        if (target.supportedLayers.isEmpty()) {
            return ValidationResult.Invalid("Architecture must support at least one layer")
        }

        target.dependencyRules.forEach { (from, dependencies) ->

            if (!target.supportedLayers.contains(from)) {
                return ValidationResult.Invalid(
                    "Dependency rule defined for unsupported layer: $from"
                )
            }

            dependencies.forEach { to ->
                if (!target.supportedLayers.contains(to)) {
                    return ValidationResult.Invalid(
                        "Layer $from depends on unsupported layer $to"
                    )
                }

                if (from == to) {
                    return ValidationResult.Invalid(
                        "Layer $from cannot depend on itself"
                    )
                }
            }
        }

        if (target.canDependOn(CoreLayer.DOMAIN, CoreLayer.DATA)) {
            return ValidationResult.Invalid(
                "DOMAIN layer cannot depend on DATA layer"
            )
        }

        return ValidationResult.Valid
    }
}