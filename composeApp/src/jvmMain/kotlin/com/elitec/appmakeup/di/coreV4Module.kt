package com.elitec.appmakeup.di

import com.elitec.appmakeup.core.v4.contracts.MapperContract
import com.elitec.appmakeup.core.v4.contracts.RepositoryContract
import com.elitec.appmakeup.core.v5.generators.CompositeLayerGenerator
import com.elitec.appmakeup.core.v5.generators.data.RepositoryImplGenerator
import com.elitec.appmakeup.core.v5.generators.domain.DomainEntityGenerator
import com.elitec.appmakeup.core.v5.generators.feature.FeatureSkeletonGenerator
import com.elitec.appmakeup.core.v5.generators.repository.RepositoryGenerator
import com.elitec.appmakeup.core.v5.generators.usecase.UseCaseGenerator
import com.elitec.appmakeup.core.v5.pipeline.DefaultGenerationStage
import com.elitec.appmakeup.core.v4.pipeline.DefaultReportingStage
import com.elitec.appmakeup.core.v5.pipeline.FileSystemWritingStage
import com.elitec.appmakeup.core.v5.pipeline.GenerationPipeline
import com.elitec.appmakeup.core.v5.pipeline.GenerationStage
import com.elitec.appmakeup.core.v5.preview.PreviewWritingStage
import com.elitec.appmakeup.core.v4.pipeline.ReportFormat
import com.elitec.appmakeup.core.v4.pipeline.ReportingStage
import com.elitec.appmakeup.core.v5.pipeline.ValidationStage
import com.elitec.appmakeup.core.v4.pipeline.WritingOptions
import com.elitec.appmakeup.core.v5.pipeline.WritingStage
import com.elitec.appmakeup.core.v5.validation.ArchitectureValidator
import com.elitec.appmakeup.core.v5.validation.DefaultValidationStage
import com.elitec.appmakeup.core.v4.validation.EntityValidator
import com.elitec.appmakeup.core.v4.validation.FeatureValidator
import com.elitec.appmakeup.core.v5.generators.data.DataRepositoryImplGenerator
import com.elitec.appmakeup.core.v5.generators.data.DtoGenerator
import com.elitec.appmakeup.core.v5.generators.data.MapperGenerator
import com.elitec.appmakeup.core.v5.generators.domain.DomainRepositoryContractGenerator
import com.elitec.appmakeup.core.v5.generators.feature.FeatureGenerator
import com.elitec.appmakeup.core.v5.generators.stage.CompositeGenerationStage
import com.elitec.appmakeup.core.v5.pipeline.DefaultPlanningStage
import com.elitec.appmakeup.core.v5.pipeline.PlanningStage
import com.elitec.appmakeup.core.v5.preview.PreviewGenerationPipeline
import com.elitec.appmakeup.projects.usecase.preview.PreviewGenerationUseCase
import org.koin.dsl.module

val coreV4Module = module {

    /* =========================================================
     * GENERATORS
     * ========================================================= */

    single { FeatureSkeletonGenerator() }
    single { DomainEntityGenerator() }
    single { RepositoryGenerator(contracts = get()) }
    single { UseCaseGenerator(contracts = get()) }
    single { RepositoryImplGenerator(contracts = get()) }
    //single { MapperGenerator(contracts = get()) }

    /* =========================================================
     * VALIDATION
     * ========================================================= */

    single { EntityValidator() }
    single { FeatureValidator(get()) }
    single { ArchitectureValidator() }

    single<ValidationStage> {
        DefaultValidationStage(
            featureValidator = get(),
            architectureValidator = get()
        )
    }

    /* =========================================================
     * PLANNING
     * ========================================================= */

    single<List<RepositoryContract>> { emptyList() }
    single<List<MapperContract>> { emptyList() }

    single<PlanningStage> {
        DefaultPlanningStage(get())
    }

    /* =========================================================
     * GENERATION
     * ========================================================= */

    single<GenerationStage> {
        CompositeGenerationStage(
            generators = listOf<FeatureGenerator>(
                DomainEntityGenerator(),
                DomainRepositoryContractGenerator(),
                DtoGenerator(),
                MapperGenerator(),
                DataRepositoryImplGenerator()
            )
        )
    }

    /* =========================================================
     * WRITING (REAL)
     * ========================================================= */

    single<WritingStage> {
        FileSystemWritingStage(
            options = WritingOptions(
                dryRun = false,
                overwrite = true
            )
        )
    }

    /* =========================================================
     * WRITING (PREVIEW)
     * ========================================================= */

    single<PreviewWritingStage> {
        PreviewWritingStage()
    }

    /* =========================================================
     * REPORTING
     * ========================================================= */

    single<ReportingStage> {
        DefaultReportingStage(ReportFormat.CLI)
    }

    /* =========================================================
     * PIPELINES
     * ========================================================= */

    // Pipeline REAL
    single<GenerationPipeline> {
        GenerationPipeline(
            validationStage = get(),
            planningStage = get(),
            generationStage = get(),
            writingStage = get<WritingStage>(),
            reportingStage = get()
        )
    }

    // Pipeline PREVIEW
    single<GenerationPipeline> {
        GenerationPipeline(
            validationStage = get(),
            planningStage = get(),
            generationStage = get(),
            writingStage = get<PreviewWritingStage>(),
            reportingStage = get()
        )
    }
}

val coreV5Module = module {
    single {
        PreviewGenerationPipeline(
            validationStage = get(),
            planningStage = get(),
            generationStage = get()
        )
    }

    factory {
        PreviewGenerationUseCase(get())
    }
}