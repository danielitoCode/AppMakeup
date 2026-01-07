package com.elitec.appmakeup.recent

interface RecentProjectsRepository {

    fun add(path: String)

    fun list(): List<String>
}