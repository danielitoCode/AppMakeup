package com.elitec.appmakeup.projects.filesystem

import java.io.File

class DirectoryCreator {
    fun create(path: String) {
        val dir = File(path)
        if (!dir.exists()) {
            dir.mkdirs()
        }
    }
}