package com.elitec.appmakeup.presentation.screens.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.elitec.appmakeup.presentation.model.FileTreeNode

@Composable
fun FileTreeView(node: FileTreeNode, indent: Int = 0) {
    when (node) {
        is FileTreeNode.Directory -> {
            Column {
                Row(
                    modifier = Modifier
                        .padding(start = (indent * 12).dp)
                        .clickable { node.expanded = !node.expanded }
                ) {
                    Text(if (node.expanded) "📂" else "📁")
                    Spacer(Modifier.width(4.dp))
                    Text(node.name)
                }

                if (node.expanded) {
                    node.children.forEach {
                        FileTreeView(it, indent + 1)
                    }
                }
            }
        }

        is FileTreeNode.File -> {
            Text(
                "📄 ${node.name}",
                modifier = Modifier.padding(start = (indent * 12).dp)
            )
        }
    }
}