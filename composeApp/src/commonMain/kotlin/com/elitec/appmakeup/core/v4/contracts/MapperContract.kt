package com.elitec.appmakeup.core.v4.contracts

import com.elitec.appmakeup.core.v4.definition.CoreEntity

data class MapperContract(
    val entity: CoreEntity,
    val from: String,
    val to: String
)