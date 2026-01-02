package com.elitec.appmakeup.domain.modeling.rules

import com.elitec.appmakeup.domain.modeling.entity.DomainEntity

object EntityRules : ModelingRule<DomainEntity> {

    override fun validate(target: DomainEntity): List<ModelingViolation> {
        val violations = mutableListOf<ModelingViolation>()

        if (target.name.isBlank()) {
            violations += ModelingViolation("Entity name cannot be empty")
        }

        val duplicatedProps = target.properties
            .groupBy { it.name }
            .filter { it.value.size > 1 }
            .keys

        duplicatedProps.forEach {
            violations += ModelingViolation(
                "Duplicated property name: $it"
            )
        }

        val duplicatedRelations = target.relationships
            .groupBy { it.name }
            .filter { it.value.size > 1 }
            .keys

        duplicatedRelations.forEach {
            violations += ModelingViolation(
                "Duplicated relationship name: $it"
            )
        }

        return violations
    }
}