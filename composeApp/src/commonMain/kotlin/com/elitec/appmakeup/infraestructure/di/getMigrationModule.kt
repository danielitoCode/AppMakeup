package com.elitec.appmakeup.infraestructure.di

import com.elitec.appmakeup.domain.migration.MigrateProjectUseCase
import com.elitec.appmakeup.domain.migration.ProjectMigration
import com.elitec.appmakeup.domain.project.impl.MigrationV1ToV2
import org.koin.core.module.Module
import org.koin.dsl.module

fun getMigrationModule(): Module = module {
    single<ProjectMigration> { MigrationV1ToV2() }
    single {
        MigrateProjectUseCase(
            migrations = getAll()
        )
    }
}