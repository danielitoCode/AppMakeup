package com.elitec.appmakeup.presentation.util

import com.elitec.appmakeup.domain.project.ProjectLocation
import java.nio.file.Paths

actual fun defaultProjectLocation(): ProjectLocation {
    val home = System.getProperty("user.home")
    return ProjectLocation(
        Paths.get(home, "Documents", "AppMake", "p1").toString()
    )
}