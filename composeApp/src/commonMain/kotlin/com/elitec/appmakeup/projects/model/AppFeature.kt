package com.elitec.appmakeup.projects.model

import com.elitec.appmakeup.core.v5.domain.contracts.EditableRepositoryContract
import kotlinx.serialization.Serializable

@Serializable
data class AppFeature(
    val name: String,
    val properties: List<AppProperty>,
    val repositoryContracts: List<EditableRepositoryContract> = emptyList()
)