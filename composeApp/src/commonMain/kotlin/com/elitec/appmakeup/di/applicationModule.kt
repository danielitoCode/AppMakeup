package com.elitec.appmakeup.di

import com.elitec.appmakeup.core.v5.pipeline.PlanningStage
import com.elitec.appmakeup.generation.GenerateCodeUseCase
import com.elitec.appmakeup.generation.PreviewGenerationPlanUseCase
import com.elitec.appmakeup.projects.usecase.feature.AddFeatureUseCase
import com.elitec.appmakeup.projects.usecase.feature.GetFeatureUseCase
import com.elitec.appmakeup.projects.usecase.feature.ListFeaturesUseCase
import com.elitec.appmakeup.projects.usecase.feature.RemoveFeatureUseCase
import com.elitec.appmakeup.projects.usecase.project.CreateProjectUseCase
import com.elitec.appmakeup.projects.usecase.project.ListRecentProjectsUseCase
import com.elitec.appmakeup.projects.usecase.project.LoadProjectUseCase
import com.elitec.appmakeup.projects.usecase.project.SaveProjectUseCase
import com.elitec.appmakeup.projects.usecase.property.AddPropertyUseCase
import com.elitec.appmakeup.projects.usecase.property.ListPropertiesUseCase
import com.elitec.appmakeup.projects.usecase.property.RemovePropertyUseCase
import org.koin.dsl.module

val applicationModule = module {

    // Project lifecycle
    factory { CreateProjectUseCase(get(), get()) }
    factory { LoadProjectUseCase(get(), get()) }
    factory { SaveProjectUseCase(get()) }

    // Features
    factory { ListFeaturesUseCase() }
    factory { GetFeatureUseCase() }
    factory { AddFeatureUseCase(get()) }
    factory { RemoveFeatureUseCase(get()) }

    // Properties
    factory { ListPropertiesUseCase() }
    factory { AddPropertyUseCase(get()) }
    factory { RemovePropertyUseCase(get()) }

    // Recents
    factory { ListRecentProjectsUseCase(get()) }

    // Generation
    factory { GenerateCodeUseCase(get()) }
    // ✅ Core V5 – Preview del plan
    factory { PreviewGenerationPlanUseCase(get())}
}