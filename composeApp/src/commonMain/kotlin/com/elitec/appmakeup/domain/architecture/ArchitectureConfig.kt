package com.elitec.appmakeup.domain.architecture

data class ArchitectureConfig(
    val layers: List<Layer>
)

enum class Layer {
    DOMAIN,
    DATA,
    PRESENTATION,
    INFRASTRUCTURE
}