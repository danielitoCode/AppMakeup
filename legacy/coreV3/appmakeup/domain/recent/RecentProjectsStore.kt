package com.elitec.appmakeup.domain.recent

import com.elitec.appmakeup.domain.model.ProjectLocation

/**
 * Port for persisting recently opened/created projects.
 *
 * Core V3: simple persistence adapter.
 * Core V4: may be replaced by settings/db.
 */
interface RecentProjectsStore {

    fun load(): List<ProjectLocation>

    fun save(projects: List<ProjectLocation>)
}