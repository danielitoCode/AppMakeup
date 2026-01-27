package com.elitec.appmakeup.projects.usecase.contracts

import com.elitec.appmakeup.core.v5.domain.contracts.EditableRepositoryContract
import com.elitec.appmakeup.projects.model.AppMakeupProject

class AddRepositoryContractUseCase {

    fun execute(
        project: AppMakeupProject,
        featureName: String,
        contract: EditableRepositoryContract
    ): AppMakeupProject {

        require(!contract.isEmpty()) {
            "Repository contract must support at least one operation"
        }

        return project.copy(
            features = project.features.map { feature ->
                if (feature.name != featureName) feature
                else feature.copy(
                    repositoryContracts =
                        feature.repositoryContracts + contract
                )
            }
        )
    }
}