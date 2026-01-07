package com.elitec.appmakeup.core.v4.templates.usecase

import com.elitec.appmakeup.core.v4.contracts.Template

class GetAllUseCaseTemplate : Template {

    override val name: String = "GetAllUseCaseTemplate"

    override fun render(model: Map<String, Any>): String {

        val packageName = model["package"] as String
        val entity = model["entity"] as String
        val repository = model["repository"] as String
        val useCaseName = "GetAll${entity}sUseCase"

        return """
            package $packageName

            class $useCaseName(
                private val repository: $repository
            ) {
                suspend operator fun invoke(): Result<List<$entity>> =
                    repository.getAll()
            }
        """.trimIndent()
    }
}