package com.elitec.appmakeup.core.v4.validation

class TemplateValidator : Validator<String> {

    override fun validate(target: String): ValidationResult {

        if (target.isBlank()) {
            return ValidationResult.Invalid("Template content cannot be blank")
        }

        if (!target.contains("{{") || !target.contains("}}")) {
            return ValidationResult.Invalid(
                "Template must contain at least one placeholder"
            )
        }

        return ValidationResult.Valid
    }
}