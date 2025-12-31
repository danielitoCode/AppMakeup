package com.elitec.appmakeup.domain.validation

sealed interface ProjectValidationResult {

    data object Valid : ProjectValidationResult

    sealed interface Invalid : ProjectValidationResult {
        data object DirectoryNotFound : Invalid
        data object MissingProjectFile : Invalid
        data object InvalidProjectFile : Invalid
        data class UnsupportedVersion(
            val projectVersion: Int,
            val supportedVersion: Int
        ) : Invalid

        data class MigratableVersion(
            val projectVersion: Int,
            val supportedVersion: Int
        ) : Invalid
    }
}