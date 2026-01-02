package com.elitec.appmakeup.domain.template

sealed interface StructureGenerationResult {

    data object Success : StructureGenerationResult

    sealed interface Failure : StructureGenerationResult {

        data class InvalidTemplate(
            val reason: String
        ) : Failure

        data class IOError(
            val message: String
        ) : Failure
    }
}