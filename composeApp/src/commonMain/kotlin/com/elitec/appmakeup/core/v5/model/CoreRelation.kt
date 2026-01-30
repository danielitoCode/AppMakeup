package com.elitec.appmakeup.core.v5.model

data class CoreRelation(
    val fromEntity: String,
    val toEntity: String,
    val type: CoreRelationType,
    val fromField: String,
    val toField: String
)

enum class CoreRelationType {
    ONE_TO_MANY,
    MANY_TO_ONE,
    MANY_TO_MANY
}