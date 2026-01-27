package com.elitec.appmakeup.core.v5.domain.contracts

import com.elitec.appmakeup.core.v4.contracts.GenerationResult

interface Generator<T> {
    fun generate(
        context: GenerationContext,
        target: T
    ): GenerationResult
}