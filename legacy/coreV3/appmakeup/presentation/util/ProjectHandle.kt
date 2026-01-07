package com.elitec.appmakeup.presentation.util

import com.elitec.appmakeup.domain.model.ProjectLocation

data class ProjectHandle(
    val workspace: ProjectLocation,
    val projectName: String
)