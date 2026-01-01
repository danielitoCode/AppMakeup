package com.elitec.appmakeup.domain.modeling.rules

import com.elitec.appmakeup.domain.modeling.entity.EntityProperty
import com.elitec.appmakeup.domain.modeling.entity.PropertyType

object PropertyRules : ModelingRule<EntityProperty> {

    override fun validate(target: EntityProperty): List<ModelingViolation> {
        val violations = mutableListOf<ModelingViolation>()

        if (target.name.isBlank()) {
            violations += ModelingViolation("Property name cannot be empty")
        }

        if (target.type is PropertyType.EnumType) {
            val enum = target.type
            if (enum.values.isEmpty()) {
                violations += ModelingViolation(
                    "Enum '${enum.name}' must define at least one value"
                )
            }
        }

        return violations
    }
}