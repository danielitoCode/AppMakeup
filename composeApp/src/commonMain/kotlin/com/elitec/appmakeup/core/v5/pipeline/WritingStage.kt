package com.elitec.appmakeup.core.v5.pipeline

import com.elitec.appmakeup.core.v4.contracts.GenerationResult
import com.elitec.appmakeup.core.v4.pipeline.GeneratedArtifact
import com.elitec.appmakeup.core.v5.domain.contracts.GenerationContext

interface WritingStage {
    fun write(
        outputPath: String,
        artifacts: List<GeneratedArtifact>,
        context: GenerationContext
    ): GenerationResult
}