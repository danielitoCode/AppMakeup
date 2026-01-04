package com.elitec.appmakeup.data.filesystem

import com.elitec.appmakeup.domain.model.Project
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class ProjectSerializer {
    fun serialize(project: Project): String {
        return Json.encodeToString(project)
    }

    fun deserialize(string: String): Project {
        return Json.decodeFromString<Project>(string)
    }
}