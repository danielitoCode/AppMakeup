package com.elitec.appmakeup.di

import com.elitec.appmakeup.exports.ProjectExporter
import com.elitec.appmakeup.generation.CodeGenerator
import com.elitec.appmakeup.generation.ProjectCodeGenerator
import com.elitec.appmakeup.projects.persistence.FileProjectPersistence
import com.elitec.appmakeup.projects.persistence.ProjectPersistence
import com.elitec.appmakeup.recent.FileRecentProjectsRepository
import com.elitec.appmakeup.recent.RecentProjectsRepository
import org.koin.dsl.module

val infrastructureModule = module {

    single<ProjectPersistence> {
        FileProjectPersistence()
    }

    single<RecentProjectsRepository> {
        FileRecentProjectsRepository()
    }

    single {
        ProjectExporter(
            pipeline = get(),
            previewPipeline = get()
        )
    }

    single<CodeGenerator> {
        ProjectCodeGenerator(get())
    }
}