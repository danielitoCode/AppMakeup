package com.elitec.appmakeup.generation.mappers

import com.elitec.appmakeup.core.v4.contracts.RepositoryContract
import com.elitec.appmakeup.core.v4.definition.CoreEntity
import com.elitec.appmakeup.core.v4.definition.CoreLayer
import com.elitec.appmakeup.core.v4.definition.CoreProperty
import com.elitec.appmakeup.core.v5.definitions.CoreFeature
import com.elitec.appmakeup.core.v5.domain.contracts.EditableRepositoryContract
import com.elitec.appmakeup.core.v5.model.CoreRelation
import com.elitec.appmakeup.core.v5.model.CoreRelationType
import com.elitec.appmakeup.projects.model.AppFeature
import com.elitec.appmakeup.projects.model.RelationType

class ProjectToCoreMapper {

    fun mapFeature(feature: AppFeature): CoreFeature {

        val coreEntities = feature.entities.map { appEntity ->
            CoreEntity(
                name = appEntity.name,
                properties = appEntity.properties.map {
                    CoreProperty(
                        name = it.name,
                        type = it.type,
                        isIdentifier = it.isIdentifier
                    )
                }
            )
        }

        fun findEntity(name: String): CoreEntity =
            coreEntities.first { it.name == name }

        val relations = feature.relations.map { r ->
            val from = findEntity(r.fromEntity).name
            val to = findEntity(r.toEntity).name

            val defaultFromField = "${to.replaceFirstChar { it.lowercase() }}List"
            val defaultToField = "${from.replaceFirstChar { it.lowercase() }}Id"

            CoreRelation(
                fromEntity = from,
                toEntity = to,
                type = when (r.type) {
                    RelationType.ONE_TO_MANY -> CoreRelationType.ONE_TO_MANY
                    RelationType.MANY_TO_ONE -> CoreRelationType.MANY_TO_ONE
                    RelationType.MANY_TO_MANY -> CoreRelationType.MANY_TO_MANY
                },
                fromField = r.fromField ?: defaultFromField,
                toField = r.toField ?: defaultToField
            )
        }

        val repositoryContracts =
            feature.repositoryContracts.map { editable ->
                // Contract por entidad (entityName)
                val entity = findEntity(editable.entityName)
                RepositoryContract(
                    entity = entity,
                    supportsCreate = editable.supportsCreate,
                    supportsRead = editable.supportsRead,
                    supportsUpdate = editable.supportsUpdate,
                    supportsDelete = editable.supportsDelete
                )
            }

        return CoreFeature(
            name = feature.name.lowercase(),
            entities = coreEntities,
            layers = setOf(CoreLayer.DOMAIN, CoreLayer.DATA),
            relations = relations,
            repositoryContracts = repositoryContracts
        )
    }
}