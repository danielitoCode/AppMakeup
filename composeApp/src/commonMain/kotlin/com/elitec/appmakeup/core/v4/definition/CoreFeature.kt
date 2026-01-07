package com.elitec.appmakeup.core.v4.definition

data class CoreFeature(
    val name: String,
    val entities: List<CoreEntity>,
    val layers: Set<CoreLayer>
)