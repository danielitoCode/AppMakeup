package com.elitec.appmakeup.domain.model

interface ProjectRepository {

    fun save(
        location: ProjectLocation,
        project: Project
    )

    fun load(
        location: ProjectLocation
    ): Project?
}