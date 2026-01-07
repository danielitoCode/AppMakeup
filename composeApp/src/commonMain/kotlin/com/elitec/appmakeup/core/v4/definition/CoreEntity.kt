package com.elitec.appmakeup.core.v4.definition

data class CoreEntity(
    val name: String,
    val properties: List<CoreProperty>
) {

    val identifier: CoreProperty
        get() = properties.firstOrNull { it.isIdentifier }
            ?: error("Entity $name must have an identifier property")

}