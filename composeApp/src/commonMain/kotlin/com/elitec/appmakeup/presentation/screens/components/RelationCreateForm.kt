package com.elitec.appmakeup.presentation.screens.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.unit.dp
import com.elitec.appmakeup.projects.model.RelationType

@Composable
fun RelationCreateForm(
    entities: List<String>,
    onSubmit: (String, String, RelationType) -> Unit
) {
    var from by remember { mutableStateOf("") }
    var to by remember { mutableStateOf("") }
    var type by remember { mutableStateOf(RelationType.ONE_TO_MANY) }

    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {

        Text("Add relation", style = MaterialTheme.typography.labelLarge)

        DropdownSelector(
            label = "From",
            options = entities,
            selected = from,
            onSelect = { from = it }
        )

        DropdownSelector(
            label = "To",
            options = entities,
            selected = to,
            onSelect = { to = it }
        )

        DropdownSelector(
            label = "Type",
            options = RelationType.values().map { it.name },
            selected = type.name,
            onSelect = { type = RelationType.valueOf(it) }
        )

        Button(
            onClick = {
                if (from.isNotBlank() && to.isNotBlank()) {
                    onSubmit(from, to, type)
                    from = ""
                    to = ""
                }
            }
        ) {
            Text("Add relation")
        }
    }
}