package com.elitec.appmakeup.projects.mappers

import com.elitec.appmakeup.core.v4.definition.CoreEntity
import com.elitec.appmakeup.core.v4.definition.CoreLayer
import com.elitec.appmakeup.core.v4.definition.CoreProperty
import com.elitec.appmakeup.core.v5.definitions.CoreFeature
import com.elitec.appmakeup.projects.model.AppFeature


class ProjectToCoreMapper {

    fun mapFeature(feature: AppFeature): CoreFeature {

        val coreEntities = feature.entities.map { entity ->
            CoreEntity(
                name = entity.name,
                properties = entity.properties.map { prop ->
                    CoreProperty(
                        name = prop.name,
                        type = prop.type,
                        isIdentifier = prop.isIdentifier
                    )
                }
            )
        }

        return CoreFeature(
            name = feature.name.lowercase(),
            entities = coreEntities,
            layers = setOf(
                CoreLayer.DOMAIN,
                CoreLayer.DATA
            )
        )
    }
}