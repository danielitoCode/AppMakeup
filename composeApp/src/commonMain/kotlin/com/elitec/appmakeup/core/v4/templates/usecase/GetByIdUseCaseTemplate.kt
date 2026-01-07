package com.elitec.appmakeup.core.v4.templates.usecase

import com.elitec.appmakeup.core.v4.contracts.Template

class GetByIdUseCaseTemplate : Template {

    override val name = "GetByIdUseCaseTemplate"

    override fun render(model: Map<String, Any>): String {

        val packageName = model["package"] as String
        val entity = model["entity"] as String
        val repository = model["repository"] as String
        val idType = model["idType"] as String
        val useCaseName = "Get${entity}ByIdUseCase"

        return """
            package $packageName

            class $useCaseName(
                private val repository: $repository
            ) {
                suspend operator fun invoke(id: $idType): Result<$entity> =
                    repository.getById(id)
            }
        """.trimIndent()
    }
}