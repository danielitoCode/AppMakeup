package com.elitec.appmakeup.core.v5.domain.contracts

import com.elitec.appmakeup.core.v4.definition.CoreEntity
import kotlinx.serialization.Serializable

@Serializable
data class EditableRepositoryContract(
    val entityName: String,
    val supportsCreate: Boolean = false,
    val supportsRead: Boolean = false,
    val supportsUpdate: Boolean = false,
    val supportsDelete: Boolean = false
) {
    fun isValid(): Boolean =
        supportsCreate || supportsRead || supportsUpdate || supportsDelete
}