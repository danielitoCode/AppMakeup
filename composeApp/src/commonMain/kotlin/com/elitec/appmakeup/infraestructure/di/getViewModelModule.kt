package com.elitec.appmakeup.infraestructure.di

import com.elitec.appmakeup.presentation.viewmodels.ModelingViewModel
import com.elitec.appmakeup.presentation.viewmodels.WelcomeViewModel
import org.koin.core.module.Module
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

fun getViewModelModule(): Module = module {
    viewModel { ModelingViewModel(get(), get(), get(), get(), get(), get()) }
    viewModel { WelcomeViewModel(get()) }
}