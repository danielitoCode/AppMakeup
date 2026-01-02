package com.elitec.appmakeup.domain.codegen

import com.elitec.appmakeup.domain.project.ProjectLocation
import com.elitec.appmakeup.domain.template.ProjectStructure

interface GeneratedTreeWriter {
    fun write(
        location: ProjectLocation,
        structure: ProjectStructure
    )
}