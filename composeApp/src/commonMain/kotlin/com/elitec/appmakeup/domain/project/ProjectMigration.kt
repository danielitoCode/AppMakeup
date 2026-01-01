package com.elitec.appmakeup.domain.project

interface rojectMigration {
    val from: Int
    val to: Int
    fun migrate(project: Project): Project
}