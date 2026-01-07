package com.elitec.appmakeup.domain.model

import kotlinx.serialization.Serializable

/**
 * Represents a relationship between two domain entities.
 *
 * This model is:
 * - Pure domain (no framework, no persistence, no UI)
 * - Serializable / future-proof
 * - Ready for code generation (Core V3+)
 */
@Serializable
data class EntityRelationship(
    val from: String,
    val to: String,
    val type: RelationshipType,
    val name: String? = null,
    val isOptional: Boolean = false
)