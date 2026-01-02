package com.elitec.appmakeup.domain.template

import com.elitec.appmakeup.domain.project.Project

interface ProjectTemplate {

    /**
     * Generates a logical project structure
     * WITHOUT touching filesystem
     */
    fun structure(project: Project): ProjectStructure
}