package com.elitec.appmakeup.domain.migration

import com.elitec.appmakeup.domain.project.Project

class MigrateProjectUseCase(
    private val migrations: List<ProjectMigration>
) {

    fun execute(project: Project, targetVersion: Int): MigrationResult {
        // implementación viene después
        TODO("Core V2 - step 2")
    }
}