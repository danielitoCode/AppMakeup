package com.elitec.appmakeup.core.v4.validation

sealed class ValidationResult {
    object Valid : ValidationResult()
    data class Invalid(val reason: String) : ValidationResult()

    fun isValid(): Boolean = this is Valid
}