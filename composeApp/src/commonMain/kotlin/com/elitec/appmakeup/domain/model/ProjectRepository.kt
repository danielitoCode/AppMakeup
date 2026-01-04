package com.elitec.appmakeup.domain.model

interface ProjectRepository {
    /**
     * Loads a project from its root location.
     * Returns null if project file does not exist or is invalid.
     */
    fun load(location: ProjectLocation): Project?
}