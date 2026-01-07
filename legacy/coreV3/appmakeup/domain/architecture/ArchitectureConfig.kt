package com.elitec.appmakeup.domain.architecture

import kotlinx.serialization.Serializable

@Serializable
data class ArchitectureConfig(
    val layers: List<Layer>
) {
    companion object {
        fun default(): ArchitectureConfig =
            ArchitectureConfig(
                layers = listOf(
                    Layer.DOMAIN,
                    Layer.DATA,
                    Layer.PRESENTATION
                )
            )
    }
}

enum class Layer {
    DOMAIN,
    DATA,
    PRESENTATION,
    INFRASTRUCTURE
}