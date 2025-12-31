package com.elitec.appmakeup.domain.modeling.entity

import com.elitec.appmakeup.domain.modeling.entity.PropertyType

data class EntityProperty(
    val name: String,
    val type: PropertyType,
    val nullable: Boolean = false,
    val isCollection: Boolean = false
)