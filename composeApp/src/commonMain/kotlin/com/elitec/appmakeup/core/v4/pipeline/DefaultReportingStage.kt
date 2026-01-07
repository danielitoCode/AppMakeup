package com.elitec.appmakeup.core.v4.pipeline

class DefaultReportingStage(
    private val format: ReportFormat = ReportFormat.CLI
) : ReportingStage {

    override fun report(
        artifacts: List<GeneratedArtifact>
    ): GenerationReport {

        val report = GenerationReport(
            generatedFiles = artifacts.size,
            files = artifacts.map { it.relativePath }
        )

        val reporter: Reporter = when (format) {
            ReportFormat.CLI -> CliReporter()
            ReportFormat.TABLE -> TableReporter()
            ReportFormat.JSON -> JsonReporter()
        } as Reporter

        reporter.output(report)
        return report
    }
}