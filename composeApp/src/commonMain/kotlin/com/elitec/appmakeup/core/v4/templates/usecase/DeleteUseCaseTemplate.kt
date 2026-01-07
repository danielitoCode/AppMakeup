package com.elitec.appmakeup.core.v4.templates.usecase

import com.elitec.appmakeup.core.v4.contracts.Template

class DeleteUseCaseTemplate : Template {

    override val name: String = "DeleteUseCaseTemplate"

    override fun render(model: Map<String, Any>): String {

        val packageName = model["package"] as String
        val entity = model["entity"] as String
        val repository = model["repository"] as String
        val idType = model["idType"] as String
        val useCaseName = "Delete${entity}UseCase"

        return """
            package $packageName

            class $useCaseName(
                private val repository: $repository
            ) {
                suspend operator fun invoke(id: $idType): Result<Unit> =
                    repository.delete(id)
            }
        """.trimIndent()
    }
}