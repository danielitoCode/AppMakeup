package com.elitec.appmakeup.core.v4.templates.mapper

import com.elitec.appmakeup.core.v4.contracts.MapperContract
import com.elitec.appmakeup.core.v4.contracts.Template

class MapperTemplate : Template {

    override val name: String = "MapperTemplate"

    override fun render(model: Map<String, Any>): String {

        val packageName = model["package"] as String
        val contract = model["contract"] as MapperContract
        val entity = contract.entity

        val fromType = contract.from
        val toType = contract.to

        val functionName =
            "to${toType.substringAfterLast(".")}"

        val mappings = entity.properties.joinToString(",\n") {
            "        ${it.name} = this.${it.name}"
        }

        return """
            package $packageName

            fun $fromType.$functionName(): $toType =
                $toType(
        $mappings
                )
        """.trimIndent()
    }
}