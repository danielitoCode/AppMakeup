package com.elitec.appmakeup.core.v4.pipeline

interface Reporter {
    fun output(report: GenerationReport)
}