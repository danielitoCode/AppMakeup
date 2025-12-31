package com.elitec.appmakeup.presentation.cache

import com.elitec.appmakeup.domain.project.ProjectLocation
import io.github.reactivecircus.cache4k.Cache

object RecentProjectsCache {
    private val cache = Cache.Builder<String, List<ProjectLocation>>()
        .maximumCacheSize(1)
        .build()

    private const val KEY = "recent-projects"

    fun get(): List<ProjectLocation> =
        cache.get(KEY) ?: emptyList()

    fun add(location: ProjectLocation) {
        val updated = (get() + location).distinct()
        cache.put(KEY, updated)
    }
}