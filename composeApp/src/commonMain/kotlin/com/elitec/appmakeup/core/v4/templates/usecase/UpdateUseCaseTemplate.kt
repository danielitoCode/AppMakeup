package com.elitec.appmakeup.core.v4.templates.usecase

import com.elitec.appmakeup.core.v4.contracts.Template

class UpdateUseCaseTemplate : Template {

    override val name: String = "UpdateUseCaseTemplate"

    override fun render(model: Map<String, Any>): String {

        val packageName = model["package"] as String
        val entity = model["entity"] as String
        val repository = model["repository"] as String
        val useCaseName = "Update${entity}UseCase"

        return """
            package $packageName

            class $useCaseName(
                private val repository: $repository
            ) {
                suspend operator fun invoke(entity: $entity): Result<Unit> =
                    repository.update(entity)
            }
        """.trimIndent()
    }
}