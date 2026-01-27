package com.elitec.appmakeup.core.v4.definition

import kotlinx.serialization.Serializable

@Serializable
data class CoreProperty(
    val name: String,
    val type: String,
    val nullable: Boolean = false,
    val mutable: Boolean = false,
    val isIdentifier: Boolean = false
)