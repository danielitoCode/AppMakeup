package com.elitec.appmakeup.projects.usecase.entity

import com.elitec.appmakeup.projects.model.AppMakeupProject

class DeleteEntityPropertyUseCase {

    fun execute(
        project: AppMakeupProject,
        featureName: String,
        entityName: String,
        propertyName: String
    ): AppMakeupProject {

        return project.copy(
            features = project.features.map { feature ->
                if (feature.name != featureName) return@map feature

                feature.copy(
                    entities = feature.entities.map { entity ->
                        if (entity.name != entityName) return@map entity

                        val property =
                            entity.properties.find { it.name == propertyName }
                                ?: return@map entity

                        // 🔐 Reglas Core V5
                        require(!property.isIdentifier) {
                            "Identifier property cannot be removed"
                        }

                        require(entity.properties.size > 1) {
                            "Entity '${entity.name}' must have at least one property"
                        }

                        entity.copy(
                            properties =
                                entity.properties.filterNot { it.name == propertyName }
                        )
                    }
                )
            }
        )
    }
}