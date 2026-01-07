package com.elitec.appmakeup.core.v4.pipeline

import com.elitec.appmakeup.core.v4.contracts.GenerationResult

interface WritingStage {
    fun write(
        outputPath: String,
        artifacts: List<GeneratedArtifact>
    ): GenerationResult
}