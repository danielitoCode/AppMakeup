package com.elitec.appmakeup.generation

import com.elitec.appmakeup.projects.model.AppMakeupProject

/**
 * Representa la intención explícita de generar código
 * a partir del estado actual del proyecto AppMakeup.
 */
data class CodeGenerationIntent(
    val project: AppMakeupProject,
    val exportPath: String
)