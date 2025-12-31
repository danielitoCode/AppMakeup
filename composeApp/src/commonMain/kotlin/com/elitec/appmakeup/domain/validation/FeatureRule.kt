package com.elitec.appmakeup.domain.validation

import com.elitec.appmakeup.domain.modeling.Feature

interface FeatureRule {
    fun validate(feature: Feature): List<RuleViolation>
}