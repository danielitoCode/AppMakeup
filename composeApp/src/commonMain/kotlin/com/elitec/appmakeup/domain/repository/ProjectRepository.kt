package com.elitec.appmakeup.domain.repository

import com.elitec.appmakeup.domain.project.Project

interface ProjectRepository {

    fun load(projectId: String): Project?

    fun save(project: Project)

    fun generateStructure(project: Project)

    fun export(project: Project)
}