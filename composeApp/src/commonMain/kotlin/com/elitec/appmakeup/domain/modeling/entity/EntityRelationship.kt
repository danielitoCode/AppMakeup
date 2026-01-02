package com.elitec.appmakeup.domain.modeling.entity

import kotlinx.serialization.Serializable

@Serializable
data class EntityRelationship(
    val name: String,
    val targetEntity: String,
    val type: RelationshipType,
    val nullable: Boolean = false
)