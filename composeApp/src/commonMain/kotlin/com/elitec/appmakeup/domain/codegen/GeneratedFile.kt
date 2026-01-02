package com.elitec.appmakeup.domain.codegen

data class GeneratedFile(
    override val name: String,
    val content: String
) : GeneratedNode