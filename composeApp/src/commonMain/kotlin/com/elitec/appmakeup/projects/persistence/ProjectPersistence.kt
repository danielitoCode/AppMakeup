package com.elitec.appmakeup.projects.persistence

import com.elitec.appmakeup.projects.model.AppMakeupProject

interface ProjectPersistence {

    fun save(project: AppMakeupProject)

    fun load(path: String): AppMakeupProject
}