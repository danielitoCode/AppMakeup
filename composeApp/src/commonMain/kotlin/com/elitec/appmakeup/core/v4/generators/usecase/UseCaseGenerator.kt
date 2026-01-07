package com.elitec.appmakeup.core.v4.generators.usecase

import com.elitec.appmakeup.core.v4.contracts.GenerationContext
import com.elitec.appmakeup.core.v4.contracts.RepositoryContract
import com.elitec.appmakeup.core.v4.pipeline.GeneratedArtifact
import com.elitec.appmakeup.core.v4.pipeline.LayerGenerator
import com.elitec.appmakeup.core.v4.templates.usecase.CreateUseCaseTemplate
import com.elitec.appmakeup.core.v4.templates.usecase.DeleteUseCaseTemplate
import com.elitec.appmakeup.core.v4.templates.usecase.GetAllUseCaseTemplate
import com.elitec.appmakeup.core.v4.templates.usecase.GetByIdUseCaseTemplate
import com.elitec.appmakeup.core.v4.templates.usecase.UpdateUseCaseTemplate

class UseCaseGenerator(
    private val contracts: List<RepositoryContract>
) : LayerGenerator {

    override fun generate(
        context: GenerationContext
    ): List<GeneratedArtifact> {

        val featureName = context.feature.name.lowercase()
        val artifacts = mutableListOf<GeneratedArtifact>()

        contracts
            .filter { context.feature.entities.contains(it.entity) }
            .forEach { contract ->
                artifacts += generateUseCases(featureName, contract)
            }

        return artifacts
    }

    private fun generateUseCases(
        featureName: String,
        contract: RepositoryContract
    ): List<GeneratedArtifact> {

        val entity = contract.entity
        val entityName = entity.name
        val idType = entity.identifier.type
        val repositoryName = "${entityName}Repository"
        val basePackage = "features.$featureName.domain.usecase"

        val artifacts = mutableListOf<GeneratedArtifact>()

        fun artifact(name: String, content: String) =
            GeneratedArtifact(
                "features/$featureName/domain/usecase/$name.kt",
                content
            )

        if (contract.supportsRead) {
            artifacts += artifact(
                "GetAll${entityName}sUseCase",
                GetAllUseCaseTemplate().render(
                    mapOf(
                        "package" to basePackage,
                        "entity" to entityName,
                        "repository" to repositoryName
                    )
                )
            )

            artifacts += artifact(
                "Get${entityName}ByIdUseCase",
                GetByIdUseCaseTemplate().render(
                    mapOf(
                        "package" to basePackage,
                        "entity" to entityName,
                        "repository" to repositoryName,
                        "idType" to idType
                    )
                )
            )
        }

        if (contract.supportsCreate) {
            artifacts += artifact(
                "Create${entityName}UseCase",
                CreateUseCaseTemplate().render(
                    mapOf(
                        "package" to basePackage,
                        "entity" to entityName,
                        "repository" to repositoryName
                    )
                )
            )
        }

        if (contract.supportsUpdate) {
            artifacts += artifact(
                "Update${entityName}UseCase",
                UpdateUseCaseTemplate().render(
                    mapOf(
                        "package" to basePackage,
                        "entity" to entityName,
                        "repository" to repositoryName
                    )
                )
            )
        }

        if (contract.supportsDelete) {
            artifacts += artifact(
                "Delete${entityName}UseCase",
                DeleteUseCaseTemplate().render(
                    mapOf(
                        "package" to basePackage,
                        "entity" to entityName,
                        "repository" to repositoryName,
                        "idType" to idType
                    )
                )
            )
        }

        return artifacts
    }
}