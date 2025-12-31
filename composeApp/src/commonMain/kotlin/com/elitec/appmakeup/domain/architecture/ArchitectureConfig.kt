package com.elitec.appmakeup.domain.architecture

import kotlinx.serialization.Serializable

@Serializable
data class ArchitectureConfig(
    val layers: List<Layer>
)

enum class Layer {
    DOMAIN,
    DATA,
    PRESENTATION,
    INFRASTRUCTURE
}