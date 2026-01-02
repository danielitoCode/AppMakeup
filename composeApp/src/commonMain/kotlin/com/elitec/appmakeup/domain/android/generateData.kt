package com.elitec.appmakeup.domain.android

import com.elitec.appmakeup.domain.codegen.GeneratedDirectory
import com.elitec.appmakeup.domain.codegen.GeneratedFile

internal fun generateData(
    feature: String,
    entity: String
): GeneratedDirectory =
    GeneratedDirectory(
        name = "data",
        children = listOf(
            GeneratedDirectory(
                name = "dto",
                children = listOf(
                    GeneratedFile(
                        name = "${entity}Dto.kt",
                        content = """
                            data class ${entity}Dto(
                                val id: String
                            )
                        """.trimIndent()
                    )
                )
            ),
            GeneratedDirectory(
                name = "mapper",
                children = listOf(
                    GeneratedFile(
                        name = "${entity}Mapper.kt",
                        content = """
                            fun ${entity}Dto.toDomain(): $entity =
                                $entity(id = id)
                        """.trimIndent()
                    )
                )
            )
        )
    )