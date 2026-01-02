package com.elitec.appmakeup.infraestructure.di

import com.elitec.appmakeup.data.filesystem.TemplateStructureGenerator
import com.elitec.appmakeup.domain.project.usecase.CreateProjectWithTemplateUseCase
import com.elitec.appmakeup.domain.project.usecase.SaveProjectUseCase
import com.elitec.appmakeup.domain.template.ProjectTemplate
import com.elitec.appmakeup.domain.template.StructureGenerator
import com.elitec.appmakeup.domain.template.impl.CleanTemplate
import com.elitec.appmakeup.domain.template.usecase.GenerateProjectStructureFromTemplateUseCase
import okio.FileSystem
import org.koin.core.module.Module
import org.koin.dsl.module

fun getTemplateModule(): Module = module {
    single<ProjectTemplate> { CleanTemplate() }
    single<StructureGenerator> { TemplateStructureGenerator(get()) }
    single { SaveProjectUseCase(get()) }
    single {
        CreateProjectWithTemplateUseCase(
            generateStructure = get(),
            saveProject = get()
        )
    }
    single { GenerateProjectStructureFromTemplateUseCase(get()) }
}