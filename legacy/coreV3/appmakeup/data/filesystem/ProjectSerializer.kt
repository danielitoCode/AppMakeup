package com.elitec.appmakeup.data.filesystem

import com.elitec.appmakeup.domain.model.Project
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class ProjectSerializer(
    private val json: Json
) {
    fun serialize(project: Project): String {
        return json.encodeToString(project)
    }

    fun deserialize(string: String): Project {
        return json.decodeFromString<Project>(string)
    }
}