package com.elitec.appmakeup.presentation.screens.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.elitec.appmakeup.projects.model.AppFeature
import com.elitec.appmakeup.projects.model.AppRelation

@Composable
fun RelationListPanel(
    feature: AppFeature,
    onDeleteRelation: (AppRelation) -> Unit
) {
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {

        Text(
            text = "Relations",
            style = MaterialTheme.typography.titleMedium
        )

        if (feature.relations.isEmpty()) {
            Text(
                text = "No relations defined",
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            return
        }

        feature.relations.forEach { relation ->

            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor =
                        MaterialTheme.colorScheme.surfaceVariant
                )
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    Column(Modifier.weight(1f)) {

                        Text(
                            text = "${relation.fromEntity} → ${relation.toEntity}",
                            fontWeight = FontWeight.Medium
                        )

                        Text(
                            text = relation.type.name.replace("_", " "),
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }

                    IconButton(
                        onClick = { onDeleteRelation(relation) }
                    ) {
                        Icon(
                            imageVector = Icons.Default.Delete,
                            contentDescription = "Delete relation",
                            tint = MaterialTheme.colorScheme.error
                        )
                    }
                }
            }
        }
    }
}