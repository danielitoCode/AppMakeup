package com.elitec.appmakeup.domain.project

import com.elitec.appmakeup.domain.architecture.ArchitectureConfig
import com.elitec.appmakeup.domain.modeling.Feature
import kotlinx.serialization.Serializable

@Serializable
data class Project(
    val name: String,
    val packageName: String,
    val architecture: ArchitectureConfig,
    val features: List<Feature>,
    // 👇 NUEVO (compatible)
    val version: Int = CURRENT_PROJECT_VERSION
)

const val CURRENT_PROJECT_VERSION = 1