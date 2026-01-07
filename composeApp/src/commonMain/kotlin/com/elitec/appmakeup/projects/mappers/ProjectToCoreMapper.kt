package com.elitec.appmakeup.projects.mappers

import com.elitec.appmakeup.core.v4.definition.CoreEntity
import com.elitec.appmakeup.core.v4.definition.CoreFeature
import com.elitec.appmakeup.core.v4.definition.CoreLayer
import com.elitec.appmakeup.core.v4.definition.CoreProperty
import com.elitec.appmakeup.projects.model.AppFeature


class ProjectToCoreMapper {

    fun mapFeature(feature: AppFeature): CoreFeature {

        val entity = CoreEntity(
            name = feature.name,
            properties = feature.properties.map {
                CoreProperty(
                    name = it.name,
                    type = it.type,
                    isIdentifier = it.isIdentifier
                )
            }
        )

        return CoreFeature(
            name = feature.name.lowercase(),
            entities = listOf(entity),
            layers = setOf(
                CoreLayer.DOMAIN,
                CoreLayer.DATA
            )
        )
    }
}