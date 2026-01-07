package com.elitec.appmakeup.domain.model

import kotlinx.serialization.Serializable
import kotlin.time.Clock
import kotlin.time.Instant

@Serializable
data class ProjectMetadata(
    val createdAt: Instant,
    val lastMigratedAt: Instant = Clock.System.now()
)