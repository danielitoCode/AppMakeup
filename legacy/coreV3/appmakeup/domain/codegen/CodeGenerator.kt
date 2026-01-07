package com.elitec.appmakeup.domain.codegen

import com.elitec.appmakeup.domain.model.Project

interface CodeGenerator {
    /**
     * Generates in-memory source code representation.
     * No filesystem, no templates, no IO.
     */
    fun generate(project: Project): CodeTree
}