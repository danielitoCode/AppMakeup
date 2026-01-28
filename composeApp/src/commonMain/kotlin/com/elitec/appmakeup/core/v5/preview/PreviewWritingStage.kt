package com.elitec.appmakeup.core.v5.preview

import com.elitec.appmakeup.core.v4.contracts.GenerationResult
import com.elitec.appmakeup.core.v4.pipeline.GeneratedArtifact
import com.elitec.appmakeup.core.v5.domain.contracts.GenerationContext
import com.elitec.appmakeup.core.v5.pipeline.WritingStage

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