package com.elitec.appmakeup.domain.project

import com.elitec.appmakeup.domain.architecture.ArchitectureConfig
import com.elitec.appmakeup.domain.modeling.Feature

data class Project(
    val name: String,
    val packageName: String,
    val architecture: ArchitectureConfig,
    val features: List<Feature>
)
