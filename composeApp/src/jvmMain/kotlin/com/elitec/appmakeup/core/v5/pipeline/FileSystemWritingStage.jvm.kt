package com.elitec.appmakeup.core.v5.pipeline

import com.elitec.appmakeup.core.v4.contracts.GenerationResult
import com.elitec.appmakeup.core.v4.pipeline.GeneratedArtifact
import com.elitec.appmakeup.core.v4.pipeline.WritingOptions
import com.elitec.appmakeup.core.v5.domain.contracts.GenerationContext
import com.elitec.appmakeup.logs.Logger
import java.io.File
import kotlin.text.get

class FileSystemWritingStage(
    private val options: WritingOptions = WritingOptions()
) : WritingStage {

    private val tag = "[FileSystemWritingStage] --->"

    override fun write(
        outputPath: String,
        artifacts: List<GeneratedArtifact>,
        context: GenerationContext
    ): GenerationResult {

        val dryRun = context.options["dryRun"] as? Boolean ?: false

        artifacts.forEach { artifact ->
            val file = File(outputPath, artifact.relativePath)

            if (dryRun) {
                Logger.success(tag, "[DRY-RUN] Would write: ${file.path}")
                return@forEach
            }

            file.parentFile?.mkdirs()

            if (file.exists() && !options.overwrite) {
                return GenerationResult.Failure(
                    "File already exists and overwrite=false: ${file.path}"
                )
            }

            file.writeText(artifact.content)
        }

        return GenerationResult.Success
    }
}