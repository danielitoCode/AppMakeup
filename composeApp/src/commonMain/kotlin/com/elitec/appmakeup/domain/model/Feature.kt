package com.elitec.appmakeup.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class Feature(
    val name: String,
    val entity: DomainEntity
)