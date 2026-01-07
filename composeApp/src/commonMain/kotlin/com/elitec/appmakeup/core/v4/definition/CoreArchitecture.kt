package com.elitec.appmakeup.core.v4.definition

data class CoreArchitecture(
    val supportedLayers: Set<CoreLayer>,
    val dependencyRules: Map<CoreLayer, Set<CoreLayer>>
) {

    fun canDependOn(from: CoreLayer, to: CoreLayer): Boolean {
        return dependencyRules[from]?.contains(to) ?: false
    }

}