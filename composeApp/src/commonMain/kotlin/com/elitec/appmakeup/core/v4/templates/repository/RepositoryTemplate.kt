package com.elitec.appmakeup.core.v4.templates.repository

import com.elitec.appmakeup.core.v4.contracts.RepositoryContract
import com.elitec.appmakeup.core.v4.contracts.Template

class RepositoryTemplate : Template {

    override val name: String = "RepositoryTemplate"

    override fun render(model: Map<String, Any>): String {

        val packageName = model["package"] as String
        val contract = model["contract"] as RepositoryContract
        val entity = contract.entity
        val entityName = entity.name
        val idType = entity.identifier.type

        val methods = buildList {

            if (contract.supportsRead) {
                add("    suspend fun getAll(): Result<List<$entityName>>")
                add("    suspend fun getById(id: $idType): Result<$entityName>")
            }

            if (contract.supportsCreate) {
                add("    suspend fun create(entity: $entityName): Result<Unit>")
            }

            if (contract.supportsUpdate) {
                add("    suspend fun update(entity: $entityName): Result<Unit>")
            }

            if (contract.supportsDelete) {
                add("    suspend fun delete(id: $idType): Result<Unit>")
            }
        }

        val methodsBlock = methods.joinToString("\n\n")

        return """
            package $packageName

            interface ${entityName}Repository {

        $methodsBlock
            }
        """.trimIndent()
    }
}