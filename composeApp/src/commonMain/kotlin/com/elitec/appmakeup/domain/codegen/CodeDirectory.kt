package com.elitec.appmakeup.domain.codegen

data class CodeDirectory(
    val name: String,
    val files: List<CodeFile> = emptyList(),
    val children: List<CodeDirectory> = emptyList()
)