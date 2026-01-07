package com.elitec.appmakeup.core.v4.validation

import com.elitec.appmakeup.core.v4.contracts.MapperContract

class MapperContractValidator(
    private val entityValidator: EntityValidator = EntityValidator()
) : Validator<MapperContract> {

    override fun validate(target: MapperContract): ValidationResult {

        // 1. Validar entidad
        val entityResult = entityValidator.validate(target.entity)
        if (!entityResult.isValid()) {
            return entityResult
        }

        // 2. Orígenes y destinos válidos
        if (target.from.isBlank()) {
            return ValidationResult.Invalid(
                "Mapper 'from' cannot be blank for entity ${target.entity.name}"
            )
        }

        if (target.to.isBlank()) {
            return ValidationResult.Invalid(
                "Mapper 'to' cannot be blank for entity ${target.entity.name}"
            )
        }

        // 3. No mapear a sí mismo
        if (target.from == target.to) {
            return ValidationResult.Invalid(
                "Mapper for entity ${target.entity.name} cannot map from ${target.from} to itself"
            )
        }

        return ValidationResult.Valid
    }
}