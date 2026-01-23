package com.elitec.appmakeup.presentation.util

import java.awt.FileDialog
import java.awt.Frame

actual fun pickDirectory(): String? {
    val dialog = FileDialog(Frame(), "Selecciona una carpeta")
    dialog.isVisible = true
    return dialog.directory
}