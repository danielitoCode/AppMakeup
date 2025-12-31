package com.elitec.appmakeup.domain.validation

import kotlinx.serialization.Serializable

@Serializable
data class RuleViolation(
    val message: String,
    val path: String? = null
)