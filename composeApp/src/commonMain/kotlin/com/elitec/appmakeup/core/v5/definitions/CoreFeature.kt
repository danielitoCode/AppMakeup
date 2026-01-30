package com.elitec.appmakeup.core.v5.definitions

import com.elitec.appmakeup.core.v4.contracts.RepositoryContract
import com.elitec.appmakeup.core.v4.definition.CoreEntity
import com.elitec.appmakeup.core.v4.definition.CoreLayer
import com.elitec.appmakeup.core.v5.domain.contracts.EditableRepositoryContract
import com.elitec.appmakeup.core.v5.model.CoreRelation

data class CoreFeature(
    val name: String,
    val entities: List<CoreEntity>,
    val layers: Set<CoreLayer>,
    val relations: List<CoreRelation> = emptyList(),
    val repositoryContracts: List<RepositoryContract> = emptyList()
)