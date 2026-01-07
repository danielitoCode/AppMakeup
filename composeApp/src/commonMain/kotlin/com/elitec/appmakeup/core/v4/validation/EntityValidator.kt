package com.elitec.appmakeup.core.v4.validation

import com.elitec.appmakeup.core.v4.definition.CoreEntity

class EntityValidator : Validator<CoreEntity> {

    override fun validate(target: CoreEntity): ValidationResult {

        if (target.name.isBlank()) {
            return ValidationResult.Invalid("Entity name cannot be blank")
        }

        if (target.properties.isEmpty()) {
            return ValidationResult.Invalid("Entity ${target.name} must have at least one property")
        }

        val identifiers = target.properties.filter { it.isIdentifier }
        if (identifiers.size != 1) {
            return ValidationResult.Invalid(
                "Entity ${target.name} must have exactly one identifier property"
            )
        }

        val duplicatedProperties = target.properties
            .groupBy { it.name }
            .filter { it.value.size > 1 }

        if (duplicatedProperties.isNotEmpty()) {
            return ValidationResult.Invalid(
                "Entity ${target.name} has duplicated properties: ${duplicatedProperties.keys}"
            )
        }

        return ValidationResult.Valid
    }
}