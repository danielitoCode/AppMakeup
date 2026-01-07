package com.elitec.appmakeup.core.v4.pipeline

interface ReportingStage {
    fun report(
        artifacts: List<GeneratedArtifact>
    ): GenerationReport
}