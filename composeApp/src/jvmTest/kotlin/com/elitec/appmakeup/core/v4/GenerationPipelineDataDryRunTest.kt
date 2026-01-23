package com.elitec.appmakeup.core.v4

import com.elitec.appmakeup.core.architecture.DefaultArchitecture
import com.elitec.appmakeup.core.v4.contracts.GenerationContext
import com.elitec.appmakeup.core.v4.contracts.GenerationResult
import com.elitec.appmakeup.core.v4.contracts.RepositoryContract
import com.elitec.appmakeup.core.v4.definition.CoreEntity
import com.elitec.appmakeup.core.v4.definition.CoreFeature
import com.elitec.appmakeup.core.v4.definition.CoreLayer
import com.elitec.appmakeup.core.v4.definition.CoreProperty
import com.elitec.appmakeup.core.v4.generators.CompositeLayerGenerator
import com.elitec.appmakeup.core.v4.generators.data.RepositoryImplGenerator
import com.elitec.appmakeup.core.v4.generators.domain.DomainEntityGenerator
import com.elitec.appmakeup.core.v4.generators.repository.RepositoryGenerator
import com.elitec.appmakeup.core.v4.generators.usecase.UseCaseGenerator
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

class GenerationPipelineDataDryRunTest {
    @Test
    fun `dry-run should generate domain and data artifacts when repository contract exists`() {

        // GIVEN
        val tempDir = Files.createTempDirectory("appmakeup-data-test").toFile()

        val userEntity = CoreEntity(
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

        val repositoryContract = RepositoryContract(
            entity = userEntity,
            supportsCreate = true,
            supportsRead = true,
            supportsUpdate = true,
            supportsDelete = false
        )

        val feature = CoreFeature(
            name = "User",
            layers = setOf(
                CoreLayer.DOMAIN,
                CoreLayer.DATA
            ),
            entities = listOf(userEntity)
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
            planningStage = DefaultPlanningStage(
                repositoryContracts = listOf(repositoryContract),
                mapperContracts = emptyList()
            ),
            generationStage = DefaultGenerationStage(
                domainGenerator = CompositeLayerGenerator(
                    listOf(
                        DomainEntityGenerator(),
                        RepositoryGenerator(listOf(repositoryContract)),
                        UseCaseGenerator(listOf(repositoryContract))
                    )
                ),
                dataGenerator = CompositeLayerGenerator(
                    listOf(
                        RepositoryImplGenerator(listOf(repositoryContract))
                    )
                ),
                presentationGenerator = null,
                repositoryGenerator = null,
                mapperGenerator = null
            ),
            writingStage = FileSystemWritingStage(
                WritingOptions(dryRun = true, overwrite = true)
            ),
            reportingStage = DefaultReportingStage(ReportFormat.CLI)
        )

        // WHEN
        val result = pipeline.run(context)

        // THEN
        assertTrue(
            result is GenerationResult.Success,
            "Pipeline should succeed when DATA contracts are valid"
        )

        // filesystem debe estar vacío (solo el directorio raíz)
        assertEquals(
            1,
            tempDir.walkTopDown().toList().size,
            "No files should be written in dry-run mode"
        )
    }
}