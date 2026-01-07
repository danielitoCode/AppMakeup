package com.elitec.appmakeup.domain.usecase

import com.elitec.appmakeup.domain.model.EntityProperty
import com.elitec.appmakeup.domain.model.Project

class AddEntityPropertyUseCase {

    fun execute(
        project: Project,
        featureName: String,
        property: EntityProperty
    ): Project {

        val updatedFeatures = project.features.map { feature ->
            if (feature.name == featureName) {
                feature.copy(
                    entity = feature.entity.copy(
                        properties = feature.entity.properties + property
                    )
                )
            } else feature
        }

        return project.copy(features = updatedFeatures)
    }
}