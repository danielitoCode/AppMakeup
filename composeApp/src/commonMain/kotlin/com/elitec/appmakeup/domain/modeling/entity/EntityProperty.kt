package com.elitec.appmakeup.domain.modeling.entity

import com.elitec.appmakeup.domain.modeling.entity.PropertyType
import kotlinx.serialization.Serializable

@Serializable
data class EntityProperty(
    val name: String,
    val type: PropertyType,
    val nullable: Boolean = false,
    val isCollection: Boolean = false
)