package com.elitec.appmakeup.domain.template

import com.elitec.appmakeup.domain.project.Project
import com.elitec.appmakeup.domain.project.ProjectLocation

interface StructureGenerator {

    fun generate(
        project: Project,
        location: ProjectLocation,
        template: ProjectTemplate
    ): StructureGenerationResult
}