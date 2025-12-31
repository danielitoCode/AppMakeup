package com.elitec.appmakeup.domain.validation

data class RuleViolation(
    val message: String,
    val path: String? = null
)