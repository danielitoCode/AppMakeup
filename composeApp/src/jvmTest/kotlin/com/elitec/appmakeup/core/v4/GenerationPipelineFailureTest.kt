package com.elitec.appmakeup.core.v4

import com.elitec.appmakeup.core.architecture.DefaultArchitecture
import com.elitec.appmakeup.core.v4.contracts.GenerationResult
import com.elitec.appmakeup.core.v4.definition.CoreLayer
import com.elitec.appmakeup.core.v5.generators.domain.DomainEntityGenerator
import com.elitec.appmakeup.core.v5.pipeline.DefaultGenerationStage
import com.elitec.appmakeup.core.v4.pipeline.DefaultReportingStage
import com.elitec.appmakeup.core.v5.pipeline.FileSystemWritingStage
import com.elitec.appmakeup.core.v5.pipeline.GenerationPipeline
import com.elitec.appmakeup.core.v4.pipeline.ReportFormat
import com.elitec.appmakeup.core.v4.pipeline.WritingOptions
import com.elitec.appmakeup.core.v5.validation.ArchitectureValidator
import com.elitec.appmakeup.core.v5.validation.DefaultValidationStage
import com.elitec.appmakeup.core.v4.validation.FeatureValidator
import com.elitec.appmakeup.core.v5.definitions.CoreFeature
import com.elitec.appmakeup.core.v5.domain.contracts.GenerationContext
import com.elitec.appmakeup.core.v5.pipeline.DefaultPlanningStage
import java.nio.file.Files
import kotlin.test.Test
import kotlin.test.assertTrue

class GenerationPipelineFailureTest {

    @Test
    fun `pipeline should fail when feature has no entities`() {

        // GIVEN
        val tempDir = Files.createTempDirectory("appmakeup-failure-test").toFile()

        val invalidFeature = CoreFeature(
            name = "InvalidFeature",
            layers = setOf(CoreLayer.DOMAIN),
            entities = emptyList() // ❌ inválido
        )

        val context = GenerationContext(
            architecture = DefaultArchitecture.value,
            feature = invalidFeature,
            outputPath = tempDir.absolutePath
        )

        val pipeline = GenerationPipeline(
            validationStage = DefaultValidationStage(
                featureValidator = FeatureValidator(),
                architectureValidator = ArchitectureValidator()
            ),
            planningStage = DefaultPlanningStage(),
            generationStage = DefaultGenerationStage(
                domainGenerator = DomainEntityGenerator(),
                dataGenerator = null,
                presentationGenerator = null,
                repositoryGenerator = null,
                mapperGenerator = null
            ),
            writingStage = FileSystemWritingStage(
                WritingOptions(dryRun = true)
            ),
            reportingStage = DefaultReportingStage(ReportFormat.CLI)
        )

        // WHEN
        val result = pipeline.run(context)

        // THEN
        assertTrue(
            result is GenerationResult.Failure,
            "Pipeline should fail for invalid feature"
        )

        val failure = result as GenerationResult.Failure
        assertTrue(
            failure.reason.contains("must contain at least one entity"),
            "Failure reason should explain validation error"
        )
    }
}