package com.elitec.appmakeup.projects.model

import kotlinx.serialization.Serializable

@Serializable
data class AppRelation(
    val fromEntity: String,
    val toEntity: String,
    val type: RelationType,
    val fromField: String? = null, // opcional: nombre del campo en from
    val toField: String? = null    // opcional: nombre del campo en to
)

@Serializable
enum class RelationType {
    ONE_TO_MANY,
    MANY_TO_ONE,
    MANY_TO_MANY
}