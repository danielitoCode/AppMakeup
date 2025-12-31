package com.elitec.appmakeup.feature.domainEntity.domain.entity

import com.elitec.appmakeup.feature.entityProperty.domain.entity.EntityProperty

data class DomainEntity(
    val name: String,
    val properties: List<EntityProperty>
)