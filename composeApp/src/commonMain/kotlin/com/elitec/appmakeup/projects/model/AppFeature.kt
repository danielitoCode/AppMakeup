package com.elitec.appmakeup.projects.model

import kotlinx.serialization.Serializable

@Serializable
data class AppFeature(
    val name: String,
    val properties: List<AppProperty>
)