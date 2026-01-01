package com.elitec.appmakeup.domain.migration

import com.elitec.appmakeup.domain.project.Project

sealed interface MigrationResult {

    data class Success(
        val migratedProject: Project
    ) : MigrationResult

    sealed interface Failure : MigrationResult {

        data class UnsupportedVersion(
            val from: Int,
            val to: Int
        ) : Failure

        data class InvalidProject(
            val reason: String
        ) : Failure

        data class UnexpectedError(
            val throwable: Throwable
        ) : Failure
    }
}