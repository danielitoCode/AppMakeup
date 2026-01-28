package com.elitec.appmakeup.projects.usecase.contracts

import com.elitec.appmakeup.projects.model.AppMakeupProject

class RemoveRepositoryContractUseCase {

    fun execute(
        project: AppMakeupProject,
        featureName: String,
        entityName: String
    ): AppMakeupProject {

        return project.copy(
            features = project.features.map { feature ->
                if (feature.name != featureName) feature
                else feature.copy(
                    repositoryContracts =
                        feature.repositoryContracts
                            .filterNot { it.entityName == entityName }
                )
            }
        )
    }
}