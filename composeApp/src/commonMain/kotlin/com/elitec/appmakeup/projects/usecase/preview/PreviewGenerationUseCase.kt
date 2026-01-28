package com.elitec.appmakeup.projects.usecase.preview

import com.elitec.appmakeup.core.architecture.DefaultArchitecture
import com.elitec.appmakeup.core.v5.domain.contracts.GenerationContext
import com.elitec.appmakeup.core.v5.preview.GenerationPreviewResult
import com.elitec.appmakeup.core.v5.preview.PreviewGenerationPipeline
import com.elitec.appmakeup.projects.mappers.ProjectToCoreMapper
import com.elitec.appmakeup.projects.model.AppMakeupProject

class PreviewGenerationUseCase(
    private val previewPipeline: PreviewGenerationPipeline
) {

    fun execute(project: AppMakeupProject): List<GenerationPreviewResult> {

        val mapper = ProjectToCoreMapper()
        val results = mutableListOf<GenerationPreviewResult>()

        project.features.forEach { feature ->
            val coreFeature = mapper.mapFeature(feature)

            val context = GenerationContext(
                architecture = DefaultArchitecture.value,
                feature = coreFeature,
                outputPath = "PREVIEW",
                options = mapOf("dryRun" to true)
            )

            results += previewPipeline.run(context)
        }

        return results
    }
}