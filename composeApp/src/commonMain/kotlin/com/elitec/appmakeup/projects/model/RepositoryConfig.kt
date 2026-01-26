package com.elitec.appmakeup.projects.model

import kotlinx.serialization.Serializable

@Serializable
data class RepositoryConfig(
    val supportsCreate: Boolean = false,
    val supportsRead: Boolean = true,
    val supportsUpdate: Boolean = false,
    val supportsDelete: Boolean = false
) {
    fun isValid(): Boolean =
        supportsCreate || supportsRead || supportsUpdate || supportsDelete
}