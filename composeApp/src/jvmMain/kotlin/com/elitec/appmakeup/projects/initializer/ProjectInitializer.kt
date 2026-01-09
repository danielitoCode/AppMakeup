package com.elitec.appmakeup.projects.initializer

import com.elitec.appmakeup.projects.model.AppMakeupProject
import com.elitec.appmakeup.projects.persistence.ProjectPersistence
import java.io.File

class ProjectInitializer(
    private val persistence: ProjectPersistence
) {

    fun create(project: AppMakeupProject) {

        val root = File(project.path)

        if (!root.exists()) {
            root.mkdirs()
        }

        File(root, "features").mkdirs()
        File(root, ".appmakeup").mkdirs()

        // Persistimos el estado inicial
        persistence.save(project)
    }
}