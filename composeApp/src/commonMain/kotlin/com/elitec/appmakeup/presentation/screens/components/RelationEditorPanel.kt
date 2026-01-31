package com.elitec.appmakeup.presentation.screens.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.elitec.appmakeup.projects.model.AppFeature
import com.elitec.appmakeup.projects.model.AppRelation
import com.elitec.appmakeup.projects.model.RelationType

@Composable
fun RelationEditorPanel(
    feature: AppFeature,
    onAddRelation: (AppRelation) -> Unit,
    onDeleteRelation: (AppRelation) -> Unit
) {
    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {

        Text("Relations", style = MaterialTheme.typography.titleMedium)

        if (feature.relations.isEmpty()) {
            Text(
                "No relations defined",
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        } else {
            feature.relations.forEach { relation ->
                RelationRow(
                    relation = relation,
                    onDelete = { onDeleteRelation(relation) }
                )
            }
        }

        Divider()

        RelationCreateForm(
            entities = feature.entities.map { it.name },
            onSubmit = { from, to, type ->
                onAddRelation(
                    AppRelation(
                        fromEntity = from,
                        toEntity = to,
                        type = type
                    )
                )
            }
        )
    }
}