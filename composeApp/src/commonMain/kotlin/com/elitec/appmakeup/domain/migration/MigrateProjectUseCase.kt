package com.elitec.appmakeup.domain.migration

import com.elitec.appmakeup.domain.project.Project

class MigrateProjectUseCase(
    private val migrations: List<ProjectMigration>
) {

    fun execute(
        project: Project,
        targetVersion: Int
    ): MigrationResult {

        var current = project

        while (current.version < targetVersion) {
            val migration = migrations.firstOrNull {
                it.fromVersion == current.version &&
                        it.toVersion == current.version + 1
            } ?: return MigrationResult.Failure.UnsupportedVersion(
                from = current.version,
                to = targetVersion
            )

            when (val result = migration.migrate(current)) {
                is MigrationResult.Success -> {
                    current = result.migratedProject
                }
                is MigrationResult.Failure -> {
                    return result
                }
            }
        }

        return MigrationResult.Success(current)
    }
}