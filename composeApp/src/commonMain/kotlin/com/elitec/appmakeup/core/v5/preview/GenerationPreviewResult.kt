package com.elitec.appmakeup.core.v5.preview

import com.elitec.appmakeup.core.v4.pipeline.GenerationPlan

data class GenerationPreviewResult(
    val featureName: String,
    val plan: GenerationPlan,
    val files: List<String>
)