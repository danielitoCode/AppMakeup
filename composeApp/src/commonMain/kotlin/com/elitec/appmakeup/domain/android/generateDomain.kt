package com.elitec.appmakeup.domain.android

import com.elitec.appmakeup.domain.codegen.GeneratedDirectory
import com.elitec.appmakeup.domain.codegen.GeneratedFile

internal fun generateDomain(
    feature: String,
    entity: String
): GeneratedDirectory =
    GeneratedDirectory(
        name = "domain",
        children = listOf(
            GeneratedDirectory(
                name = "entity",
                children = listOf(
                    GeneratedFile(
                        name = "$entity.kt",
                        content = """
                            data class $entity(
                                val id: String
                            )
                        """.trimIndent()
                    )
                )
            ),
            GeneratedDirectory(
                name = "repository",
                children = listOf(
                    GeneratedFile(
                        name = "${entity}Repository.kt",
                        content = """
                            interface ${entity}Repository {
                                fun getAll(): List<$entity>
                            }
                        """.trimIndent()
                    )
                )
            )
        )
    )