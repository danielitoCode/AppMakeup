package com.elitec.appmakeup.presentation.util

import io.github.vinceglb.filekit.FileKit
import io.github.vinceglb.filekit.dialogs.openDirectoryPicker
import io.github.vinceglb.filekit.path

actual suspend fun selectFolder(): String? {
    return FileKit.openDirectoryPicker()?.path
}