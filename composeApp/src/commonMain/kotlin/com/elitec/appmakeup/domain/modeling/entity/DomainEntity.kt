package com.elitec.appmakeup.domain.modeling.entity

import com.elitec.appmakeup.domain.modeling.entity.EntityProperty
import kotlinx.serialization.Serializable

@Serializable
data class DomainEntity(
    val name: String,
    val properties: List<EntityProperty>
)