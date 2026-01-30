package com.elitec.appmakeup.projects.usecase.entity

import com.elitec.appmakeup.projects.model.AppMakeupProject
import com.elitec.appmakeup.projects.model.AppProperty

class AddEntityPropertyUseCase {

    fun execute(
        project: AppMakeupProject,
        featureName: String,
        entityName: String,
        property: AppProperty
    ): AppMakeupProject {

        return project.copy(
            features = project.features.map { feature ->
                if (feature.name != featureName) return@map feature

                feature.copy(
                    entities = feature.entities.map { entity ->
                        if (entity.name != entityName) return@map entity

                        // 🔐 Reglas Core V5
                        require(entity.properties.none { it.name == property.name }) {
                            "Property '${property.name}' already exists in entity '${entity.name}'"
                        }

                        if (property.isIdentifier) {
                            require(entity.properties.none { it.isIdentifier }) {
                                "Entity '${entity.name}' already has an identifier"
                            }
                        }

                        entity.copy(
                            properties = entity.properties + property
                        )
                    }
                )
            }
        )
    }
}