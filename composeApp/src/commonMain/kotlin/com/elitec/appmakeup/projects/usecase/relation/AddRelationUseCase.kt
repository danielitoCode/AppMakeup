package com.elitec.appmakeup.projects.usecase.relation

import com.elitec.appmakeup.projects.model.AppMakeupProject
import com.elitec.appmakeup.projects.model.AppRelation
import com.elitec.appmakeup.projects.persistence.ProjectPersistence

class AddRelationUseCase(
    private val persistence: ProjectPersistence
) {

    fun execute(
        project: AppMakeupProject,
        featureName: String,
        relation: AppRelation
    ): AppMakeupProject {

        val updated = project.copy(
            features = project.features.map { feature ->
                if (feature.name == featureName) {
                    feature.copy(
                        relations = feature.relations + relation
                    )
                } else feature
            }
        )

        persistence.save(updated)
        return updated
    }
}