package com.elitec.appmakeup.core.v4.templates.domain

import com.elitec.appmakeup.core.v4.contracts.Template
import com.elitec.appmakeup.core.v4.definition.CoreEntity

class DomainEntityTemplate : Template {

    override val name: String = "DomainEntityTemplate"

    override fun render(model: Map<String, Any>): String {

        val packageName = model["package"] as String
        val entity = model["entity"] as CoreEntity

        val properties = entity.properties.joinToString(",\n") {
            val mutable = if (it.mutable) "var" else "val"
            val nullable = if (it.nullable) "?" else ""
            "    $mutable ${it.name}: ${it.type}$nullable"
        }

        return """
            package $packageName

            data class ${entity.name}(
        $properties
            )
        """.trimIndent()
    }
}