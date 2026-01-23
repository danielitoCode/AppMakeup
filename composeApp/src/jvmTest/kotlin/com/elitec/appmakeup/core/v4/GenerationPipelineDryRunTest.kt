package com.elitec.appmakeup.core.v4

import com.elitec.appmakeup.core.architecture.DefaultArchitecture
import com.elitec.appmakeup.core.v4.contracts.GenerationContext
import com.elitec.appmakeup.core.v4.contracts.GenerationResult
import com.elitec.appmakeup.core.v4.definition.CoreEntity
import com.elitec.appmakeup.core.v4.definition.CoreFeature
import com.elitec.appmakeup.core.v4.definition.CoreLayer
import com.elitec.appmakeup.core.v4.definition.CoreProperty
import com.elitec.appmakeup.core.v4.generators.CompositeLayerGenerator
import com.elitec.appmakeup.core.v4.generators.domain.DomainEntityGenerator
import com.elitec.appmakeup.core.v4.generators.repository.RepositoryGenerator
import com.elitec.appmakeup.core.v4.pipeline.DefaultGenerationStage
import com.elitec.appmakeup.core.v4.pipeline.DefaultPlanningStage
import com.elitec.appmakeup.core.v4.pipeline.DefaultReportingStage
import com.elitec.appmakeup.core.v4.pipeline.FileSystemWritingStage
import com.elitec.appmakeup.core.v4.pipeline.GenerationPipeline
import com.elitec.appmakeup.core.v4.pipeline.ReportFormat
import com.elitec.appmakeup.core.v4.pipeline.WritingOptions
import com.elitec.appmakeup.core.v4.validation.ArchitectureValidator
import com.elitec.appmakeup.core.v4.validation.DefaultValidationStage
import com.elitec.appmakeup.core.v4.validation.FeatureValidator
import java.nio.file.Files
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class GenerationPipelineDryRunTest {
    @Test
    fun `dry-run should generate domain artifacts without writing files`() {

        val tempDir = Files.createTempDirectory("appmakeup-test").toFile()

        val feature = CoreFeature(
            name = "User",
            layers = setOf(CoreLayer.DOMAIN),
            entities = listOf(
                CoreEntity(
                    name = "User",
                    properties = listOf(
                        CoreProperty("id", "String", isIdentifier = true),
                        CoreProperty("name", "String")
                    )
                )
            )
        )

        val context = GenerationContext(
            architecture = DefaultArchitecture.value,
            feature = feature,
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
                WritingOptions(dryRun = true, overwrite = true)
            ),
            reportingStage = DefaultReportingStage(ReportFormat.CLI)
        )

        val result = pipeline.run(context)

        assertTrue(result is GenerationResult.Success)

        // filesystem vacío
        assertEquals(
            1,
            tempDir.walkTopDown().toList().size
        )
    }
}

private fun pipeline(): GenerationPipeline =
    GenerationPipeline(
        validationStage = DefaultValidationStage(
            featureValidator = FeatureValidator(),
            architectureValidator = ArchitectureValidator()
        ),
        planningStage = DefaultPlanningStage(
            repositoryContracts = emptyList(),
            mapperContracts = emptyList()
        ),
        generationStage = DefaultGenerationStage(
            domainGenerator = CompositeLayerGenerator(
                listOf(
                    DomainEntityGenerator()
                )
            ),
            dataGenerator = CompositeLayerGenerator(
                listOf(
                    RepositoryGenerator(emptyList())
                )
            ),
            presentationGenerator = null,
            repositoryGenerator = null,
            mapperGenerator = null
        ),
        writingStage = dryRunWritingStage(),
        reportingStage = DefaultReportingStage(ReportFormat.CLI)
    )

private fun dryRunWritingStage() =
    FileSystemWritingStage(
        options = WritingOptions(
            dryRun = true,
            overwrite = true
        )
    )

private fun sampleContext(outputPath: String) =
    GenerationContext(
        architecture = DefaultArchitecture.value,
        feature = sampleFeature(),
        outputPath = outputPath
    )

private fun sampleFeature(): CoreFeature =
    CoreFeature(
        name = "User",
        layers = setOf(
            CoreLayer.DOMAIN,
            CoreLayer.DATA
        ),
        entities = listOf(
            CoreEntity(
                name = "User",
                properties = listOf(
                    CoreProperty(
                        name = "id",
                        type = "String",
                        isIdentifier = true
                    ),
                    CoreProperty(
                        name = "name",
                        type = "String"
                    )
                )
            )
        )
    )