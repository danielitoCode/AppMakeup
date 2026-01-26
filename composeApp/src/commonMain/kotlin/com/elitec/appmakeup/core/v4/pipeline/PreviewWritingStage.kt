package com.elitec.appmakeup.core.v4.pipeline

import com.elitec.appmakeup.core.v4.contracts.GenerationContext
import com.elitec.appmakeup.core.v4.contracts.GenerationResult

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
}