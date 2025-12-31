package com.elitec.appmakeup.infraestructure.di

import com.elitec.appmakeup.domain.usecase.AddEntityPropertyUseCase
import com.elitec.appmakeup.domain.usecase.AddFeatureUseCase
import com.elitec.appmakeup.domain.usecase.CreateProjectUseCase
import com.elitec.appmakeup.domain.usecase.GenerateProjectStructureUseCase
import com.elitec.appmakeup.domain.usecase.ValidateProjectUseCase
import org.koin.core.module.Module
import org.koin.dsl.module

fun getTestCaseUseModule(): Module = module {
    single { AddEntityPropertyUseCase(get()) }
    single { AddFeatureUseCase(get()) }
    single { CreateProjectUseCase(get()) }
    single { GenerateProjectStructureUseCase(get()) }
    single {
        ValidateProjectUseCase(
            rules = getAll()
        )
    }
}