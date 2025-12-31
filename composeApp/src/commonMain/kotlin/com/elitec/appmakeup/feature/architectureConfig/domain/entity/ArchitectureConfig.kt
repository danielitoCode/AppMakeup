package com.elitec.appmakeup.feature.architectureConfig.domain.entity

data class ArchitectureConfig(
    val layers: List<Layer>
)

enum class Layer {
    DOMAIN,
    DATA,
    PRESENTATION,
    INFRASTRUCTURE
}