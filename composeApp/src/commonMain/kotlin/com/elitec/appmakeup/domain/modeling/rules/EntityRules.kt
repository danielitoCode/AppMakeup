package com.elitec.appmakeup.domain.modeling.rules

import com.elitec.appmakeup.domain.modeling.entity.DomainEntity

object EntityRules : ModelingRule<DomainEntity> {

    override fun validate(target: DomainEntity): List<ModelingViolation> {
        val violations = mutableListOf<ModelingViolation>()

        if (target.name.isBlank()) {
            violations += ModelingViolation("Entity name cannot be empty")
        }

        val duplicated = target.properties
            .groupBy { it.name }
            .filter { it.value.size > 1 }
            .keys

        duplicated.forEach {
            violations += ModelingViolation(
                "Duplicated property name: $it"
            )
        }

        return violations
    }
}