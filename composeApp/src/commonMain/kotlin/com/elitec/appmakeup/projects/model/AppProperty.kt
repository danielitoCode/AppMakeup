package com.elitec.appmakeup.projects.model

import kotlinx.serialization.Serializable

@Serializable
data class AppProperty(
    val name: String,
    val type: String,
    val isIdentifier: Boolean = false
)