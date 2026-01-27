package com.elitec.appmakeup.core.v5.definitions

import com.elitec.appmakeup.core.v4.definition.CoreEntity
import com.elitec.appmakeup.core.v4.definition.CoreLayer
import com.elitec.appmakeup.core.v5.domain.contracts.EditableRepositoryContract

data class CoreFeature(
    val name: String,
    val entities: List<CoreEntity>,
    val layers: Set<CoreLayer>,
    val repositoryContracts: List<EditableRepositoryContract> = emptyList()
)