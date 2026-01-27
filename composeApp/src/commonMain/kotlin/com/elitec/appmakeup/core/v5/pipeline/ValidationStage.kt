package com.elitec.appmakeup.core.v5.pipeline

import com.elitec.appmakeup.core.v4.validation.ValidationResult
import com.elitec.appmakeup.core.v5.domain.contracts.GenerationContext

interface ValidationStage {
    fun validate(context: GenerationContext): ValidationResult
}