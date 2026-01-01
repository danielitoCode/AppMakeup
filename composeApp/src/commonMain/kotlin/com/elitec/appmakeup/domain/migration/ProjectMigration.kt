package com.elitec.appmakeup.domain.migration

import com.elitec.appmakeup.domain.project.Project

interface ProjectMigration {

    val fromVersion: Int
    val toVersion: Int

    fun migrate(project: Project): MigrationResult
}