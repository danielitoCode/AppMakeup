package com.elitec.appmakeup.domain.project.impl

import com.elitec.appmakeup.domain.migration.MigrationResult
import com.elitec.appmakeup.domain.migration.ProjectMigration
import com.elitec.appmakeup.domain.project.Project
import com.elitec.appmakeup.domain.project.entity.ProjectMetadata
import kotlin.time.Clock

class MigrationV1ToV2 : ProjectMigration {

    override val fromVersion: Int = 1
    override val toVersion: Int = 2

    override fun migrate(project: Project): MigrationResult {
        if (project.version != fromVersion) {
            return MigrationResult.Failure.UnsupportedVersion(
                from = project.version,
                to = toVersion
            )
        }

        val migrated = project.copy(
            version = toVersion,
            metadata = ProjectMetadata(
                createdAt = project.metadata.createdAt,
                lastMigratedAt = Clock.System.now()
            )
        )

        return MigrationResult.Success(migrated)
    }
}