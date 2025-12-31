package com.elitec.appmakeup.presentation.util

import com.elitec.appmakeup.domain.project.ProjectLocation
import okio.Path.Companion.toPath

data class CreateProjectConfig(
    val appName: String,
    val packageName: String,
    val workspacePath: String
) {
    fun toProjectLocation(): ProjectLocation {
        val rootPath = workspacePath.toPath() / appName
        return ProjectLocation(rootPath.toString())
    }
}