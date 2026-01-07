package com.elitec.appmakeup.projects.filesystem

import java.io.File


class FileCopier {

    fun copy(source: File, target: File) {
        if (source.isDirectory) {
            target.mkdirs()
            source.listFiles()?.forEach {
                copy(it, File(target, it.name))
            }
        } else {
            source.copyTo(target, overwrite = true)
        }
    }
}