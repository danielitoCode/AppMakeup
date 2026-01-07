package com.elitec.appmakeup.core.v4.validation

interface Validator<T> {
    fun validate(target: T): ValidationResult
}