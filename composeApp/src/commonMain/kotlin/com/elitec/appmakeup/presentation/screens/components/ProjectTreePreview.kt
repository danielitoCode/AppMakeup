package com.elitec.appmakeup.presentation.screens.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.elitec.appmakeup.presentation.model.buildProjectTree
import com.elitec.appmakeup.projects.model.AppMakeupProject
import io.github.vooft.compose.treeview.core.TreeView

@Composable
fun ProjectTreePreview(
    project: AppMakeupProject,
    modifier: Modifier = Modifier
) {
    val tree = buildProjectTree(project)
    TreeView(
        tree = tree,
        onClick = { node ->
            println("Clicked on ${node.name}")
        },
        modifier = modifier
    )
}