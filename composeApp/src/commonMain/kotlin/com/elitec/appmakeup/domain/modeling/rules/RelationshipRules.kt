package com.elitec.appmakeup.domain.modeling.rules

import com.elitec.appmakeup.domain.modeling.entity.EntityRelationship

object RelationshipRules : ModelingRule<EntityRelationship> {

    override fun validate(target: EntityRelationship): List<ModelingViolation> {
        val violations = mutableListOf<ModelingViolation>()

        if (target.name.isBlank()) {
            violations += ModelingViolation(
                "Relationship name cannot be empty"
            )
        }

        if (target.targetEntity.isBlank()) {
            violations += ModelingViolation(
                "Target entity must be specified"
            )
        }

        return violations
    }
}