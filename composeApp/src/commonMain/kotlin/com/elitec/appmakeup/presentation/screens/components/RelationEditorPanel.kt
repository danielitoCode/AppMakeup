package com.elitec.appmakeup.presentation.screens.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
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
import com.elitec.appmakeup.projects.model.RelationType

@Composable
fun RelationEditorPanel(
    feature: AppFeature,
    onAddRelation: (String, String, RelationType) -> Unit
) {
    val entityNames = remember(feature) { feature.entities.map { it.name } }

    var fromEntity by remember { mutableStateOf("") }
    var toEntity by remember { mutableStateOf("") }
    var type by remember { mutableStateOf(RelationType.ONE_TO_MANY) }

    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {

        Text(
            text = "Relations",
            style = MaterialTheme.typography.titleMedium
        )

        DropdownSelector(
            label = "From entity",
            options = entityNames,
            selected = fromEntity,
            onSelect = { fromEntity = it }
        )

        DropdownSelector(
            label = "To entity",
            options = entityNames,
            selected = toEntity,
            onSelect = { toEntity = it }
        )

        DropdownSelector(
            label = "Relation type",
            options = RelationType.values().map { it.name },
            selected = type.name,
            onSelect = { type = RelationType.valueOf(it) }
        )

        Button(
            onClick = {
                if (fromEntity.isNotBlank() && toEntity.isNotBlank()) {
                    onAddRelation(fromEntity, toEntity, type)
                }
            }
        ) {
            Text("Add relation")
        }
    }
}