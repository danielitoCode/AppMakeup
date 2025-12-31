package com.elitec.appmakeup.infraestructure.di

import com.elitec.appmakeup.presentation.settings.AppSettingsStore
import com.elitec.appmakeup.presentation.settings.RecentProjectsRepository
import org.koin.core.module.Module
import org.koin.dsl.module

fun getCacheModules(): Module = module {
    single { AppSettingsStore(get(), get()) }
    single { RecentProjectsRepository(get()) }
}