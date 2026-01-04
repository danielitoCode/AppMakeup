package com.elitec.appmakeup.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class EntityProperty(
    val name: String,
    val type: PropertyType,
    val nullable: Boolean = false,
    val isCollection: Boolean = false
)