package com.elitec.appmakeup.recent

import java.io.File

class FileRecentProjectsRepository : RecentProjectsRepository {

    private val file: File by lazy {
        val home = System.getProperty("user.home")
        val dir = File(home, ".appmakeup")
        if (!dir.exists()) dir.mkdirs()
        File(dir, "recent-projects.txt")
    }

    override fun add(path: String) {
        val current = if (file.exists()) file.readLines() else emptyList()
        val updated = listOf(path) + current.filterNot { it == path }
        file.writeText(updated.joinToString("\n"))
    }

    override fun list(): List<String> {
        return if (file.exists()) file.readLines().filter { it.isNotBlank() }
        else emptyList()
    }
}