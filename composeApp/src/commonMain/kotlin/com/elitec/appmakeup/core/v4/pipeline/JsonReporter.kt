package com.elitec.appmakeup.core.v4.pipeline

class JsonReporter : Reporter {

    override fun output(report: GenerationReport) {
        val json = buildString {
            append("{\n")
            append("  \"generatedFiles\": ${report.generatedFiles},\n")
            append("  \"files\": [\n")

            report.files.forEachIndexed { index, file ->
                append("    \"$file\"")
                if (index < report.files.lastIndex) append(",")
                append("\n")
            }

            append("  ]\n")
            append("}")
        }

        println(json)
    }
}