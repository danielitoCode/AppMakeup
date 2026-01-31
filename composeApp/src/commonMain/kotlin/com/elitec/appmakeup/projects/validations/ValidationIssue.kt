package com.elitec.appmakeup.projects.validations

enum class ValidationLevel {
    ERROR,
    WARNING
}

enum class ValidationScope {
    PROJECT,
    FEATURE,
    ENTITY,
    RELATION,
    REPOSITORY
}

data class ValidationIssue(
    val level: ValidationLevel,
    val scope: ValidationScope,
    val feature: String? = null,
    val entity: String? = null,
    val message: String
)