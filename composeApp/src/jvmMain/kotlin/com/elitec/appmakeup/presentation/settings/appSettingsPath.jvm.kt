package com.elitec.appmakeup.presentation.settings

import okio.Path
import okio.Path.Companion.toPath

actual fun appSettingsPath(): Path {
    return System.getProperty("user.home").toPath() / ".appmakeup"
}