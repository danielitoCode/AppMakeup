package com.elitec.appmakeup.projects.persistence

import com.elitec.appmakeup.projects.model.AppMakeupProject
import kotlinx.serialization.json.Json
import java.io.File

class FileProjectPersistence : ProjectPersistence {

    private val json = Json {
        prettyPrint = true
        encodeDefaults = true
    }

    override fun save(project: AppMakeupProject) {
        val file = File(project.path, "appmakeup.json")
        file.writeText(json.encodeToString(AppMakeupProject.serializer(), project))
    }

    override fun load(path: String): AppMakeupProject {
        val file = File(path, "appmakeup.json")
        return json.decodeFromString(
            AppMakeupProject.serializer(),
            file.readText()
        )
    }
}