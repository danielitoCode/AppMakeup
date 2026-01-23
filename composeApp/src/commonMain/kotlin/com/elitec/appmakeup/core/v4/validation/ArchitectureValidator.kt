package com.elitec.appmakeup.core.v4.validation

import com.elitec.appmakeup.core.v4.definition.CoreArchitecture
import com.elitec.appmakeup.core.v4.definition.CoreFeature
import com.elitec.appmakeup.core.v4.definition.CoreLayer

class ArchitectureValidator : Validator<Pair<CoreArchitecture, CoreFeature>> {

    override fun validate(
        target: Pair<CoreArchitecture, CoreFeature>
    ): ValidationResult {

        val (architecture, feature) = target

        // 1️⃣ Layers soportadas
        val unsupported = feature.layers - architecture.supportedLayers
        if (unsupported.isNotEmpty()) {
            return ValidationResult.Invalid(
                "Feature '${feature.name}' uses unsupported layers: $unsupported"
            )
        }

        // 2️⃣ Reglas de dependencia
        architecture.dependencyRules.forEach { (layer, allowedDeps) ->
            if (feature.layers.contains(layer)) {
                val invalidDeps = feature.layers - allowedDeps - layer
                if (invalidDeps.isNotEmpty()) {
                    return ValidationResult.Invalid(
                        "Layer $layer cannot depend on $invalidDeps"
                    )
                }
            }
        }

        return ValidationResult.Valid
    }
}