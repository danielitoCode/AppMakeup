package com.elitec.appmakeup.domain.modeling.rules

interface ModelingRule<T> {
    fun validate(target: T): List<ModelingViolation>
}