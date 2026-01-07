package com.elitec.appmakeup.projects.usecase.property

import com.elitec.appmakeup.projects.model.AppMakeupProject
import com.elitec.appmakeup.projects.model.AppProperty

class ListPropertiesUseCase {

    fun execute(
        project: AppMakeupProject,
        featureName: String
    ): List<AppProperty> {

        val feature = project.features
            .firstOrNull { it.name == featureName }
            ?: error("Feature '$featureName' not found")

        return feature.properties
    }
}