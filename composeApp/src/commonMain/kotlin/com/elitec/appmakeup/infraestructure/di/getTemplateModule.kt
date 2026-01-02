package com.elitec.appmakeup.infraestructure.di

import com.elitec.appmakeup.domain.template.ProjectTemplate
import com.elitec.appmakeup.domain.template.impl.CleanTemplate
import org.koin.core.module.Module
import org.koin.dsl.module

fun getTemplateModule(): Module = module {
    single<ProjectTemplate> { CleanTemplate() }
}