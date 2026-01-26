package com.elitec.appmakeup.di

import com.elitec.appmakeup.core.v4.contracts.MapperContract
import com.elitec.appmakeup.core.v4.contracts.RepositoryContract
import com.elitec.appmakeup.core.v4.generators.CompositeLayerGenerator
import com.elitec.appmakeup.core.v4.generators.data.RepositoryImplGenerator
import com.elitec.appmakeup.core.v4.generators.domain.DomainEntityGenerator
import com.elitec.appmakeup.core.v4.generators.feature.FeatureSkeletonGenerator
import com.elitec.appmakeup.core.v4.generators.mapper.MapperGenerator
import com.elitec.appmakeup.core.v4.generators.repository.RepositoryGenerator
import com.elitec.appmakeup.core.v4.generators.usecase.UseCaseGenerator
import com.elitec.appmakeup.core.v4.pipeline.DefaultGenerationStage
import com.elitec.appmakeup.core.v4.pipeline.DefaultPlanningStage
import com.elitec.appmakeup.core.v4.pipeline.DefaultReportingStage
import com.elitec.appmakeup.core.v4.pipeline.FileSystemWritingStage
import com.elitec.appmakeup.core.v4.pipeline.GenerationPipeline
import com.elitec.appmakeup.core.v4.pipeline.GenerationStage
import com.elitec.appmakeup.core.v4.pipeline.PlanningStage
import com.elitec.appmakeup.core.v4.pipeline.PreviewWritingStage
import com.elitec.appmakeup.core.v4.pipeline.ReportFormat
import com.elitec.appmakeup.core.v4.pipeline.ReportingStage
import com.elitec.appmakeup.core.v4.pipeline.ValidationStage
import com.elitec.appmakeup.core.v4.pipeline.WritingOptions
import com.elitec.appmakeup.core.v4.pipeline.WritingStage
import com.elitec.appmakeup.core.v4.validation.ArchitectureValidator
import com.elitec.appmakeup.core.v4.validation.DefaultValidationStage
import com.elitec.appmakeup.core.v4.validation.EntityValidator
import com.elitec.appmakeup.core.v4.validation.FeatureValidator
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
    single { MapperGenerator(contracts = get()) }

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
        DefaultPlanningStage(
            repositoryContracts = get(),
            mapperContracts = get()
        )
    }

    /* =========================================================
     * GENERATION
     * ========================================================= */

    single<GenerationStage> {
        DefaultGenerationStage(
            domainGenerator = CompositeLayerGenerator(
                listOf(
                    get<DomainEntityGenerator>(),
                    get<RepositoryGenerator>(),
                    get<UseCaseGenerator>()
                )
            ),
            dataGenerator = CompositeLayerGenerator(
                listOf(
                    get<RepositoryImplGenerator>(),
                    get<MapperGenerator>()
                )
            ),
            presentationGenerator = null,
            repositoryGenerator = null,
            mapperGenerator = null
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