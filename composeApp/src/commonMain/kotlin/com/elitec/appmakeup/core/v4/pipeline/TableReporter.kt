package com.elitec.appmakeup.core.v4.pipeline

class TableReporter : Reporter {

    override fun output(report: GenerationReport) {
        println("+----+----------------------------------------------+")
        println("| #  | File                                         |")
        println("+----+----------------------------------------------+")

        report.files.forEachIndexed { index, file ->
            println(
                "| ${index + 1}".padEnd(4) +
                        "| $file".padEnd(46) + "|"
            )
        }

        println("+----+----------------------------------------------+")
        println("Total: ${report.generatedFiles} files")
    }
}