package com.elitec.appmakeup.domain.model

object ProjectSession {
    var current: Project? = null
        private set

    fun open(project: Project) {
        current = project
    }

    fun clear() {
        current = null
    }
}