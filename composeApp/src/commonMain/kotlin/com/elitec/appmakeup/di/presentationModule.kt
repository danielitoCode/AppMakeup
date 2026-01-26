package com.elitec.appmakeup.di

import com.elitec.appmakeup.presentation.viewmodels.CreateProjectViewModel
import com.elitec.appmakeup.presentation.viewmodels.ExportViewModel
import com.elitec.appmakeup.presentation.viewmodels.HomeViewModel
import com.elitec.appmakeup.presentation.viewmodels.ProjectEditorViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val presentationModule = module {

    viewModel {
        HomeViewModel(
            listRecentProjectsUseCase = get()
        )
    }

    viewModel {
        CreateProjectViewModel(
            createProjectUseCase = get()
        )
    }

    viewModel {
        ProjectEditorViewModel(
            loadProjectUseCase = get(),
            saveProjectUseCase = get(),
            addFeatureUseCase = get(),
            removeFeatureUseCase = get(),
            addPropertyUseCase = get(),
            removePropertyUseCase = get(),
            generateCodeUseCase = get()
        )
    }

    viewModel {
        ExportViewModel(
            loadProjectUseCase = get(),
            generateCodeUseCase = get(),
            previewGenerationPlanUseCase = get()
        )
    }
}