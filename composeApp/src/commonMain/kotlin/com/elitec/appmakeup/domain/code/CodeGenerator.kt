package com.elitec.appmakeup.domain.code

import com.elitec.appmakeup.domain.project.Project

interface CodeGenerator {
    /**
     * Generates source code representation for a project.
     *
     * This method:
     * - does NOT write files to disk
     * - does NOT access filesystem
     * - does NOT depend on UI or platform
     *
     * It only returns an in-memory representation of the code.
     */
    fun generate(project: Project): CodeTree
}