package com.elitec.appmakeup.feature.project.domain.entity

import com.elitec.appmakeup.feature.architectureConfig.domain.entity.ArchitectureConfig
import com.elitec.appmakeup.feature.feature.domain.entity.Feature

data class Project(
    val name: String,
    val packageName: String,
    val architecture: ArchitectureConfig,
    val features: List<Feature>
)
