package com.elitec.appmakeup.presentation.screens.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.elitec.appmakeup.projects.model.AppRelation

@Composable
fun RelationRow(
    relation: AppRelation,
    onDelete: () -> Unit
) {
    Row(
        Modifier
            .fillMaxWidth()
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            "${relation.fromEntity} → ${relation.toEntity} (${relation.type})",
            modifier = Modifier.weight(1f)
        )

        IconButton(onClick = onDelete) {
            Icon(Icons.Default.Delete, contentDescription = "Delete relation")
        }
    }
}