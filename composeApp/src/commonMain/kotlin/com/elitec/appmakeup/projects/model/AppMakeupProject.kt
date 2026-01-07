package com.elitec.appmakeup.projects.model

import kotlinx.serialization.Serializable

@Serializable
data class AppMakeupProject(
    val name: String,
    val packageName: String,
    val path: String,
    val features: List<AppFeature>
)