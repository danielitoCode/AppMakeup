package com.elitec.appmakeup.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class DomainEntity(
    val name: String,
    val properties: List<EntityProperty>,
    val relationships: List<EntityRelationship> = emptyList()
)