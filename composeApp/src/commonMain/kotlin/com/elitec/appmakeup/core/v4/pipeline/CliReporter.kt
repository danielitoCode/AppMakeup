package com.elitec.appmakeup.core.v4.pipeline

class CliReporter : Reporter {

    override fun output(report: GenerationReport) {
        println("✅ Generation completed")
        println("📁 Files generated: ${report.generatedFiles}")
        report.files.forEach {
            println("  └─ $it")
        }
    }
}