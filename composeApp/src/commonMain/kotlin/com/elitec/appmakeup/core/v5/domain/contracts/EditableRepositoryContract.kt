package com.elitec.appmakeup.core.v5.domain.contracts

import com.elitec.appmakeup.core.v4.definition.CoreEntity
import kotlinx.serialization.Serializable

@Serializable
data class EditableRepositoryContract(
    val entity: CoreEntity,

    val supportsCreate: Boolean = false,
    val supportsRead: Boolean = true,
    val supportsUpdate: Boolean = false,
    val supportsDelete: Boolean = false
) {
    fun isEmpty(): Boolean =
        !supportsCreate &&
                !supportsRead &&
                !supportsUpdate &&
                !supportsDelete
}