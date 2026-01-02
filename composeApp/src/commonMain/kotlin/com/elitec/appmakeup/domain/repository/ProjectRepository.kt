package com.elitec.appmakeup.domain.repository

import com.elitec.appmakeup.domain.project.Project
import com.elitec.appmakeup.domain.project.ProjectLocation

interface ProjectRepository {

    fun load(
        location: ProjectLocation,
        projectName: String
    ): Project?

    fun save(
        location: ProjectLocation,
        project: Project
    )

    fun generateStructure(
        location: ProjectLocation,
        project: Project
    )

    fun export(
        location: ProjectLocation,
        project: Project
    )
}