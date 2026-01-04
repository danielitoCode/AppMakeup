package com.elitec.appmakeup.domain.codegen

fun CodeDirectory.walk(
    action: (CodeDirectory) -> Unit
) {
    action(this)
    children.forEach { it.walk(action) }
}