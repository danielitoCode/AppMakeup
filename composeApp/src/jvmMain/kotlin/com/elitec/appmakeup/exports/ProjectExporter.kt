package com.elitec.appmakeup.exports

import com.elitec.appmakeup.core.architecture.DefaultArchitecture
import com.elitec.appmakeup.core.v4.contracts.GenerationContext
import com.elitec.appmakeup.core.v4.pipeline.GenerationPipeline
import com.elitec.appmakeup.projects.mappers.ProjectToCoreMapper
import com.elitec.appmakeup.projects.model.AppMakeupProject

class ProjectExporter(
    private val pipeline: GenerationPipeline
) {

    fun export(project: AppMakeupProject, dryRun: Boolean) {

        val packagePath = project.packageName.replace(".", "/")

        val outputPath =
            "${project.path}/export/composeApp/src/androidMain/kotlin/$packagePath"

        project.features.forEach { feature ->

            val coreFeature =
                ProjectToCoreMapper().mapFeature(feature)

            pipeline.run(
                GenerationContext(
                    architecture = DefaultArchitecture.value,
                    feature = coreFeature,
                    outputPath = outputPath,
                    options = mapOf("dryRun" to dryRun)
                )
            )
        }
    }
}