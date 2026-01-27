package com.elitec.appmakeup.exports

import com.elitec.appmakeup.core.architecture.DefaultArchitecture
import com.elitec.appmakeup.core.v4.contracts.GenerationResult
import com.elitec.appmakeup.core.v5.domain.contracts.GenerationContext
import com.elitec.appmakeup.core.v5.pipeline.GenerationPipeline
import com.elitec.appmakeup.logs.Logger
import com.elitec.appmakeup.projects.mappers.ProjectToCoreMapper
import com.elitec.appmakeup.projects.model.AppMakeupProject

class ProjectExporter(
    private val pipeline: GenerationPipeline,
    private val previewPipeline: GenerationPipeline
) {

    private val tag = "ProjectExporter::DESKTOP"

    fun export(
        project: AppMakeupProject,
        dryRun: Boolean
    ): GenerationResult {

        Logger.success(
            tag,
            "Init generation code in platform (dryRun=$dryRun)"
        )

        val packagePath = project.packageName.replace(".", "/")
        Logger.success(
            tag,
            "Generation code package path: $packagePath"
        )

        val outputPath =
            "${project.path}/export/composeApp/src/androidMain/kotlin/$packagePath"

        Logger.success(
            tag,
            "Generation output path: $outputPath"
        )

        val pipelineToUse =
            if (dryRun) previewPipeline else pipeline

        var lastResult: GenerationResult = GenerationResult.Success

        project.features.forEach { feature ->

            Logger.warning(
                tag,
                "Scanning feature: ${feature.name}"
            )

            val coreFeature =
                ProjectToCoreMapper().mapFeature(feature)

            val result = pipelineToUse.run(
                GenerationContext(
                    architecture = DefaultArchitecture.value,
                    feature = coreFeature,
                    outputPath = outputPath,
                    options = mapOf("dryRun" to dryRun)
                )
            )

            when (result) {
                is GenerationResult.Failure -> {
                    Logger.error(
                        tag,
                        "Generation failed for feature ${feature.name}",
                        RuntimeException(result.reason)
                    )
                    return result
                }

                is GenerationResult.Preview -> {
                    Logger.success(
                        tag,
                        "Preview generated for feature ${feature.name} (${result.files.size} files)"
                    )
                    lastResult = result
                }

                GenerationResult.Success -> {
                    Logger.success(
                        tag,
                        "Generation completed for feature ${feature.name}"
                    )
                    lastResult = result
                }
            }
        }

        return lastResult
    }

    fun preview(project: AppMakeupProject): List<String> {
        val allFiles = mutableListOf<String>()

        val packagePath = project.packageName.replace(".", "/")
        val outputPath =
            "${project.path}/export/composeApp/src/androidMain/kotlin/$packagePath"

        project.features.forEach { feature ->
            val coreFeature = ProjectToCoreMapper().mapFeature(feature)

            val result = previewPipeline.run(
                GenerationContext(
                    architecture = DefaultArchitecture.value,
                    feature = coreFeature,
                    outputPath = outputPath,
                    options = mapOf("dryRun" to true)
                )
            )

            if (result is GenerationResult.Preview) {
                allFiles += result.files
            }
        }

        return allFiles
    }
}