package com.elitec.appmakeup.feature.entityProperty.domain.entity

import com.elitec.appmakeup.feature.propertyType.domain.entity.PropertyType

data class EntityProperty(
    val name: String,
    val type: PropertyType,
    val nullable: Boolean = false,
    val isCollection: Boolean = false
)