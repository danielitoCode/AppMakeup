package com.elitec.appmakeup.core.v4.contracts

import com.elitec.appmakeup.core.v4.definition.CoreEntity

data class RepositoryContract(
    val entity: CoreEntity,
    val supportsCreate: Boolean = true,
    val supportsRead: Boolean = true,
    val supportsUpdate: Boolean = true,
    val supportsDelete: Boolean = true
) {

    val isCrud: Boolean
        get() = supportsCreate && supportsRead && supportsUpdate && supportsDelete
}