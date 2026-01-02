package com.elitec.appmakeup.domain.codegen

data class GeneratedDirectory(
    override val name: String,
    val children: List<GeneratedNode> = emptyList()
) : GeneratedNode