package com.elitec.appmakeup.core.v4.contracts

interface Generator<T> {
    fun generate(
        context: GenerationContext,
        target: T
    ): GenerationResult
}