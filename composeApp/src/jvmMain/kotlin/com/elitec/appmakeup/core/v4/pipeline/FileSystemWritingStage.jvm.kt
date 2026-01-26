package com.elitec.appmakeup.core.v4.pipeline

import com.elitec.appmakeup.core.v4.contracts.GenerationContext
import com.elitec.appmakeup.core.v4.contracts.GenerationResult
import java.io.File

class FileSystemWritingStage(
    private val options: WritingOptions = WritingOptions()
) : WritingStage {

    override fun write(
        outputPath: String,
        artifacts: List<GeneratedArtifact>,
        context: GenerationContext
    ): GenerationResult {

        val dryRun = context.options["dryRun"] as? Boolean ?: false

        artifacts.forEach { artifact ->
            val file = File(outputPath, artifact.relativePath)

            if (dryRun) {
                println("[DRY-RUN] Would write: ${file.path}")
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