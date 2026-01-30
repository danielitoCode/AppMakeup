package com.elitec.appmakeup.projects.usecase.property

import com.elitec.appmakeup.projects.model.AppMakeupProject
import com.elitec.appmakeup.projects.model.AppProperty

class ListEntityPropertiesUseCase {

    fun execute(
        project: AppMakeupProject,
        featureName: String,
        entityName: String
    ): List<AppProperty> {

        val feature = project.features
            .firstOrNull { it.name == featureName }
            ?: error("Feature '$featureName' not found")

        val entity = feature.entities
            .firstOrNull { it.name == entityName }
            ?: error("Entity '$entityName' not found in feature '$featureName'")

        return entity.properties
    }
}