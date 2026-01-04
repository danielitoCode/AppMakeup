package com.elitec.appmakeup.domain.model

/**
 * Supported relationship types between entities.
 *
 * These are abstracted from any ORM or framework.
 */
enum class RelationshipType {
    ONE_TO_ONE,
    ONE_TO_MANY,
    MANY_TO_ONE,
    MANY_TO_MANY
}