package com.elitec.appmakeup.presentation.settings

import com.elitec.appmakeup.domain.model.ProjectLocation
import okio.Path.Companion.toPath

fun ProjectLocation.normalized(): ProjectLocation =
    ProjectLocation(
        value.toPath().normalized().toString()
    )