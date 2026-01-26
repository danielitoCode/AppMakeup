package com.elitec.appmakeup.core.v4.contracts

sealed class GenerationResult {
    object Success : GenerationResult()
    data class Failure(val reason: String) : GenerationResult()
    data class Preview(val files: List<String>) : GenerationResult()

    fun isSuccess(): Boolean = this is Success
}