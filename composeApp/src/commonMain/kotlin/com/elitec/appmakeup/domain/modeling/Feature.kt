package com.elitec.appmakeup.domain.modeling

import com.elitec.appmakeup.domain.modeling.entity.DomainEntity

data class Feature(
    val name: String,
    val entity: DomainEntity
)