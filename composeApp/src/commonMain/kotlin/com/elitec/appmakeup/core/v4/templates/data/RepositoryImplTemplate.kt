package com.elitec.appmakeup.core.v4.templates.data

import com.elitec.appmakeup.core.v4.contracts.RepositoryContract
import com.elitec.appmakeup.core.v4.contracts.Template

class RepositoryImplTemplate : Template {

    override val name: String = "RepositoryImplTemplate"

    override fun render(model: Map<String, Any>): String {

        val packageName = model["package"] as String
        val contract = model["contract"] as RepositoryContract
        val entity = contract.entity
        val entityName = entity.name
        val idType = entity.identifier.type
        val repositoryInterface = "${entityName}Repository"
        val implName = "${entityName}RepositoryImpl"

        fun notImplemented(returnType: String) =
            "Result.failure(NotImplementedError(\"Not implemented yet\"))"

        val methods = buildList {

            if (contract.supportsRead) {
                add(
                    """
                    override suspend fun getAll(): Result<List<$entityName>> =
                        ${notImplemented("List<$entityName>")}
                    """.trimIndent()
                )

                add(
                    """
                    override suspend fun getById(id: $idType): Result<$entityName> =
                        ${notImplemented(entityName)}
                    """.trimIndent()
                )
            }

            if (contract.supportsCreate) {
                add(
                    """
                    override suspend fun create(entity: $entityName): Result<Unit> =
                        ${notImplemented("Unit")}
                    """.trimIndent()
                )
            }

            if (contract.supportsUpdate) {
                add(
                    """
                    override suspend fun update(entity: $entityName): Result<Unit> =
                        ${notImplemented("Unit")}
                    """.trimIndent()
                )
            }

            if (contract.supportsDelete) {
                add(
                    """
                    override suspend fun delete(id: $idType): Result<Unit> =
                        ${notImplemented("Unit")}
                    """.trimIndent()
                )
            }
        }

        val methodsBlock = methods.joinToString("\n\n")

        return """
            package $packageName

            class $implName : $repositoryInterface {

            $methodsBlock
            }
        """.trimIndent()
    }
}