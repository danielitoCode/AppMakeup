package com.elitec.appmakeup.core.v5.preview

import com.elitec.appmakeup.core.v4.contracts.GenerationResult
import com.elitec.appmakeup.core.v4.pipeline.GeneratedArtifact
import com.elitec.appmakeup.core.v5.domain.contracts.GenerationContext
import com.elitec.appmakeup.core.v5.pipeline.WritingStage
import com.elitec.appmakeup.logs.Logger

/*
class PreviewWritingStage : WritingStage {

    override fun write(
        outputPath: String,
        artifacts: List<GeneratedArtifact>,
        context: GenerationContext
    ): GenerationResult {
        return GenerationResult.Preview(
            files = artifacts.map { it.relativePath }
        )
    }
}*/

class PreviewWritingStage : WritingStage {

    private val tag = "[PreviewWritingStage] ---> "

    override fun write(
        outputPath: String,
        artifacts: List<GeneratedArtifact>,
        context: GenerationContext
    ): GenerationResult {

        val files = artifacts.map {
            // 🔑 CLAVE CORE V5:
            // solo relativePath, normalizado
            it.relativePath.replace("\\", "/")
        }

        Logger.success(tag, "[PREVIEW] Files:")
        println("[PREVIEW] Files:")
        files.forEach { println(" - $it") }

        return GenerationResult.Preview(files)
    }
}