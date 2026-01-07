package com.elitec.appmakeup.domain.codegen

fun CodeDirectory.walkFiles(
    action: (CodeFile, CodeDirectory) -> Unit
) {
    files.forEach { action(it, this) }
    children.forEach { it.walkFiles(action) }
}