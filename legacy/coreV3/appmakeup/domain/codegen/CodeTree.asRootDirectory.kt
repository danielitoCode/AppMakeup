package com.elitec.appmakeup.domain.codegen

fun CodeTree.asRootDirectory(name: String): CodeDirectory =
    CodeDirectory(
        name = name,
        children = directories
    )