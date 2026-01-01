package com.elitec.appmakeup.domain.project

import com.elitec.appmakeup.domain.architecture.ArchitectureConfig
import com.elitec.appmakeup.domain.modeling.Feature
import com.elitec.appmakeup.domain.project.entity.ProjectMetadata
import kotlinx.serialization.Serializable
import kotlin.time.Clock

@Serializable
data class Project(
    val name: String,
    val packageName: String,
    val architecture: ArchitectureConfig,
    val features: List<Feature>,
    // 👇 NUEVO (compatible)
    val version: Int = CURRENT_PROJECT_VERSION,
    // ⬅️ NUEVO EN V2
    val metadata: ProjectMetadata = ProjectMetadata(
        createdAt = Clock.System.now()
    )
)

const val CURRENT_PROJECT_VERSION = 1