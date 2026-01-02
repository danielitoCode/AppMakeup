package com.elitec.appmakeup.domain.android

import com.elitec.appmakeup.domain.codegen.GeneratedDirectory
import com.elitec.appmakeup.domain.codegen.GeneratedFile

internal fun generatePresentation(
    feature: String,
    entity: String
): GeneratedDirectory =
    GeneratedDirectory(
        name = "presentation",
        children = listOf(
            GeneratedDirectory(
                name = "viewmodel",
                children = listOf(
                    GeneratedFile(
                        name = "${entity}ViewModel.kt",
                        content = """
                            import androidx.lifecycle.ViewModel

                            class ${entity}ViewModel : ViewModel()
                        """.trimIndent()
                    )
                )
            ),
            GeneratedDirectory(
                name = "screen",
                children = listOf(
                    GeneratedFile(
                        name = "${entity}Screen.kt",
                        content = """
                            import androidx.compose.runtime.Composable

                            @Composable
                            fun ${entity}Screen() {}
                        """.trimIndent()
                    )
                )
            ),
            GeneratedDirectory(
                name = "navigation",
                children = listOf(
                    GeneratedFile(
                        name = "${feature}NavGraph.kt",
                        content = """
                            object ${feature.capitalize()}NavGraph {
                                const val route = "$feature"
                            }
                        """.trimIndent()
                    )
                )
            ),
            GeneratedDirectory(
                name = "util",
                children = listOf(
                    GeneratedDirectory(
                        name = "uiStates",
                        children = listOf(
                            GeneratedFile(
                                name = "${entity}UiState.kt",
                                content = """
                                    sealed interface ${entity}UiState {
                                        data object Idle : ${entity}UiState
                                    }
                                """.trimIndent()
                            )
                        )
                    )
                )
            )
        )
    )