package com.elitec.appmakeup.presentation.model

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.elitec.appmakeup.projects.model.AppMakeupProject
import io.github.vooft.compose.treeview.core.node.Branch
import io.github.vooft.compose.treeview.core.node.Leaf
import io.github.vooft.compose.treeview.core.tree.Tree
import androidx.compose.ui.Modifier

@Composable
fun buildProjectTree(project: AppMakeupProject, modifier: Modifier = Modifier): Tree<String> = Tree {

    Branch(
        customIcon = { Text("📦 ") },
        customName = { Text(project.packageName.ifBlank { "root" }) },
        content = project.packageName.ifBlank { "root" }
    ) {
        Branch(
            customIcon = { Text("📂 ") },
            customName = { Text("features") },
            content = "features"
        ) {
            project.features.forEach { feature ->
                Branch(
                    customIcon = { Text("📂 ") },
                    customName = { Text(feature.name.lowercase()) },
                    content = feature.name.lowercase()
                ) {

                    // DOMAIN
                    Branch(
                        customIcon = { Text("📂 ") },
                        customName = { Text("domain") },
                        content = "domain"
                    ) {
                        Branch(
                            customIcon = { Text("📂 ") },
                            customName = { Text("entities") },
                            content = "entities"
                        ) {
                            feature.entities.forEach { entity ->
                                Leaf(
                                    customIcon = { Text("📄 ") },
                                    customName = { Text("${entity.name}.kt") },
                                    content = "${entity.name}.kt"
                                )
                            }
                        }
                        Branch(
                            customIcon = { Text("📂 ") },
                            customName = { Text("repositories") },
                            content = "repositories"
                        )
                        Branch(
                            customIcon = { Text("📂 ") },
                            customName = { Text("usecases") },
                            content = "usecases"
                        )
                    }

                    // DATA
                    Branch(
                        customIcon = { Text("📂 ") },
                        customName = { Text("data") },
                        content = "data"
                    ) {
                        Branch(
                            customIcon = { Text("📂 ") },
                            customName = { Text("dto") },
                            content = "dto"
                        )
                        Branch(
                            customIcon = { Text("📂 ") },
                            customName = { Text("mappers") },
                            content = "mappers"
                        )
                        Branch(
                            customIcon = { Text("📂 ") },
                            customName = { Text("repositories") },
                            content = "repositories"
                        )
                    }

                    // PRESENTATION
                    Branch(
                        customIcon = { Text("📂 ") },
                        customName = { Text("presentation") },
                        content = "presentation"
                    ) {
                        Branch(
                            customIcon = { Text("📂 ") },
                            customName = { Text("screens") },
                            content = "screens"
                        )
                        Branch(
                            customIcon = { Text("📂 ") },
                            customName = { Text("viewmodels") },
                            content = "viewmodels"
                        )
                        Branch(
                            customIcon = { Text("📂 ") },
                            customName = { Text("uiStates") },
                            content = "uiStates"
                        )
                        Branch(
                            customIcon = { Text("📂 ") },
                            customName = { Text("components") },
                            content = "components"
                        )
                        Branch(
                            customIcon = { Text("📂 ") },
                            customName = { Text("navigation") },
                            content = "navigation"
                        )
                        Branch(
                            customIcon = { Text("📂 ") },
                            customName = { Text("util") },
                            content = "util"
                        )
                    }
                }
            }
        }
    }
}