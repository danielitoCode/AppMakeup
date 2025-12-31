package com.elitec.appmakeup.domain.project

interface ProjectMigration {
    val from: Int
    val to: Int
    fun migrate(project: Project): Project
}