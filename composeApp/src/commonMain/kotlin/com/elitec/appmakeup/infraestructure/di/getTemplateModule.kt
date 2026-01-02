package com.elitec.appmakeup.infraestructure.di

import com.elitec.appmakeup.data.filesystem.OkioGeneratedTreeWriter
import com.elitec.appmakeup.data.filesystem.TemplateStructureGenerator
import com.elitec.appmakeup.domain.android.AndroidCleanCodeGenerator
import com.elitec.appmakeup.domain.codegen.CodeGenerator
import com.elitec.appmakeup.domain.codegen.GeneratedTreeWriter
import com.elitec.appmakeup.domain.project.usecase.CreateProjectWithTemplateUseCase
import com.elitec.appmakeup.domain.project.usecase.SaveProjectUseCase
import com.elitec.appmakeup.domain.template.ProjectTemplate
import com.elitec.appmakeup.domain.template.StructureGenerator
import com.elitec.appmakeup.domain.template.impl.CleanTemplate
import com.elitec.appmakeup.domain.template.usecase.GenerateProjectStructureFromTemplateUseCase
import org.koin.core.module.Module
import org.koin.dsl.module
import kotlin.math.sin

fun getTemplateModule(): Module = module {

    // Template
    single<ProjectTemplate> { CleanTemplate() }

    // Code generator (DOMINIO)
    single<CodeGenerator> { AndroidCleanCodeGenerator() }

    single<GeneratedTreeWriter> { OkioGeneratedTreeWriter(get()) }
    // 🔴 ESTE ERA EL QUE FALTABA
    single<StructureGenerator> {
        TemplateStructureGenerator(
            writer = get()
        )
    }
    // Use case: generate tree from template
    single {
        GenerateProjectStructureFromTemplateUseCase(
            generator = get()
        )
    }

    // Save project metadata
    single {
        SaveProjectUseCase(
            repository = get()
        )
    }

    // Orchestrator (THIS WAS MISSING)
    single {
        CreateProjectWithTemplateUseCase(
            generateStructure = get(),
            saveProject = get()
        )
    }
}