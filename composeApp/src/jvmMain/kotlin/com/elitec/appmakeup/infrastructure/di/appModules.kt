package com.elitec.appmakeup.infrastructure.di

import com.elitec.appmakeup.data.filesystem.LocalProjectRepository
import com.elitec.appmakeup.data.filesystem.OkioCodeTreeWriter
import com.elitec.appmakeup.data.filesystem.ProjectSerializer
import com.elitec.appmakeup.domain.codegen.AndroidCleanCodeGenerator
import com.elitec.appmakeup.domain.codegen.CodeGenerator
import com.elitec.appmakeup.domain.codegen.CodeTreeWriter
import com.elitec.appmakeup.domain.model.ProjectRepository
import com.elitec.appmakeup.domain.usecase.AddEntityPropertyUseCase
import com.elitec.appmakeup.domain.usecase.AddFeatureUseCase
import com.elitec.appmakeup.domain.usecase.CreateProjectUseCase
import com.elitec.appmakeup.domain.usecase.GenerateCodeTreeUseCase
import com.elitec.appmakeup.domain.usecase.LoadProjectUseCase
import com.elitec.appmakeup.domain.usecase.ScaffoldProjectUseCase
import com.elitec.appmakeup.domain.usecase.ValidateProjectUseCase
import com.elitec.appmakeup.domain.usecase.WriteCodeTreeUseCase
import com.elitec.appmakeup.infrastructure.di.persistence.FileProjectRepository
import com.elitec.appmakeup.infrastructure.di.persistence.ProjectPersistenceAdapter
import com.elitec.appmakeup.presentation.viewmodels.ModelingViewModel
import com.elitec.appmakeup.presentation.viewmodels.WelcomeViewModel
import okio.FileSystem
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

/**
 * Single source of truth for DI in CoreV3.
 *
 * - Domain contracts (CodeGenerator, CodeTreeWriter)
 * - Data implementations (OkioCodeTreeWriter)
 * - Use cases
 * - ViewModels
 */
val appModules = module {

    // Platform / IO
    single<FileSystem> { FileSystem.SYSTEM }

    // Code generation (pure)
    single<CodeGenerator> { AndroidCleanCodeGenerator() }

    // Serielizer
    single { ProjectSerializer() }

    // Writing (IO)
    single<CodeTreeWriter> { OkioCodeTreeWriter(get()) }

    // Adapter
    single { ProjectPersistenceAdapter(get()) }

    // Use cases
    factory { AddEntityPropertyUseCase() }
    factory { AddFeatureUseCase() }
    factory { CreateProjectUseCase(get()) }
    factory { ValidateProjectUseCase() }
    factory { GenerateCodeTreeUseCase(generator = get()) }
    factory { WriteCodeTreeUseCase(writer = get()) }
    factory { ScaffoldProjectUseCase(get(),  get()) }
    factory { LoadProjectUseCase(get()) }

    // Domain
    single<ProjectRepository> { FileProjectRepository(get(), get()) }

    // ViewModels
    viewModel { WelcomeViewModel(get(), get()) }
    viewModel { ModelingViewModel(get(), get(), get(), get()) }
}
