package com.elitec.appmakeup.core.v4.pipeline

import com.elitec.appmakeup.core.v4.contracts.GenerationResult
import java.io.File

class FileSystemWritingStage(
    private val options: WritingOptions = WritingOptions()
) : WritingStage {

    override fun write(
        outputPath: String,
        artifacts: List<GeneratedArtifact>
    ): GenerationResult {

        artifacts.forEach { artifact ->

            val file = File(outputPath, artifact.relativePath)

            if (options.dryRun) {
                // Simulación: no tocar filesystem
                println("[DRY-RUN] Would write: ${file.path}")
                return@forEach
            }

            // Crear directorios si no existen
            file.parentFile?.let { parent ->
                if (!parent.exists()) {
                    parent.mkdirs()
                }
            }

            // Archivo ya existe
            if (file.exists() && !options.overwrite) {
                return GenerationResult.Failure(
                    "File already exists and overwrite=false: ${file.path}"
                )
            }

            // Escritura real
            file.writeText(artifact.content)
        }

        return GenerationResult.Success
    }
}