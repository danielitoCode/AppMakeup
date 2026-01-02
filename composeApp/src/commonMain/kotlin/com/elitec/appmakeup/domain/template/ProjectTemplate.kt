package com.elitec.appmakeup.domain.template

interface ProjectTemplate {

    val id: String
    val displayName: String
    val description: String

    fun structure(): ProjectStructure
}