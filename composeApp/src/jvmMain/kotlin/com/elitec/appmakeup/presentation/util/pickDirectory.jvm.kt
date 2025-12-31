package com.elitec.appmakeup.presentation.util

import javax.swing.JFileChooser
import javax.swing.UIManager

actual fun pickDirectory(): String? {
    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName())

    val chooser = JFileChooser().apply {
        fileSelectionMode = JFileChooser.DIRECTORIES_ONLY
        dialogTitle = "Select Project Directory"
    }

    return if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
        chooser.selectedFile.absolutePath
    } else {
        null
    }
}