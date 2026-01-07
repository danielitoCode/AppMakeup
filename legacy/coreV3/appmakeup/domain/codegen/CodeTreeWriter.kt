package com.elitec.appmakeup.domain.codegen

import com.elitec.appmakeup.domain.model.ProjectLocation

/**
 * Output port.
 *
 * Writes an in-memory [CodeTree] to a physical location.
 * Implementations live in data/infra layers.
 */
interface CodeTreeWriter {
    fun write(location: ProjectLocation, tree: CodeTree)
}
