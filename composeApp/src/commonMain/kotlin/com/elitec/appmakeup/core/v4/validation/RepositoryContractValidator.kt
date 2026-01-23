package com.elitec.appmakeup.core.v4.validation

import com.elitec.appmakeup.core.v4.contracts.RepositoryContract

class RepositoryContractValidator(
    private val entityValidator: EntityValidator = EntityValidator()
) : Validator<RepositoryContract> {

    override fun validate(target: RepositoryContract): ValidationResult {

        // 1️⃣ Validar entidad
        val entityResult = entityValidator.validate(target.entity)
        if (!entityResult.isValid()) return entityResult

        // 2️⃣ Al menos una operación
        if (
            !target.supportsCreate &&
            !target.supportsRead &&
            !target.supportsUpdate &&
            !target.supportsDelete
        ) {
            return ValidationResult.Invalid(
                "Repository for entity ${target.entity.name} must support at least one operation"
            )
        }

        // 3️⃣ Operaciones de escritura requieren identificador
        val hasWriteOperations =
            target.supportsCreate || target.supportsUpdate || target.supportsDelete

        if (hasWriteOperations && target.entity.identifier == null) {
            return ValidationResult.Invalid(
                "Repository with write operations requires entity ${target.entity.name} to have an identifier"
            )
        }

        return ValidationResult.Valid
    }
}