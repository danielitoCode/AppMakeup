package com.elitec.appmakeup.presentation.screens.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.elitec.appmakeup.presentation.states.ProjectEditorUiState
import com.elitec.appmakeup.projects.model.AppProperty

@Composable
fun ProjectEditorContent(
    state: ProjectEditorUiState,
    onSelectFeature: (String) -> Unit,
    onAddFeature: (String) -> Unit,
    onRemoveFeature: (String) -> Unit,
    onAddProperty: (String, String, AppProperty) -> Unit,
    onRemoveProperty: (String, String, String) -> Unit,
    onExport: (Boolean) -> Unit
) {
    Column(Modifier.fillMaxSize().padding(16.dp)) {

        Text("Project Editor", style = MaterialTheme.typography.headlineSmall)

        Spacer(Modifier.height(12.dp))

        state.features.forEach { feature ->
            Card(Modifier.fillMaxWidth().padding(vertical = 8.dp)) {
                Column(Modifier.padding(12.dp)) {

                    Row {
                        Text(feature.name, style = MaterialTheme.typography.titleMedium)
                        Spacer(Modifier.weight(1f))
                        IconButton(onClick = { onRemoveFeature(feature.name) }) {
                            Text("🗑")
                        }
                    }

                    feature.entities.forEach { entity ->
                        Text("Entity: ${entity.name}")

                        entity.properties.forEach { prop ->
                            Row {
                                Text("- ${prop.name}: ${prop.type}")
                                Spacer(Modifier.weight(1f))
                                if (!prop.isIdentifier) {
                                    TextButton(
                                        onClick = {
                                            onRemoveProperty(feature.name, entity.name, prop.name)
                                        }
                                    )  { Text("Remove") }
                                }
                            }
                        }

                        Button(
                            onClick = {
                                onAddProperty(
                                    feature.name,
                                    entity.name,
                                    AppProperty(
                                        name = "field${entity.properties.size}",
                                        type = "String",
                                        isIdentifier = false
                                    )
                                )
                            }
                        ) {
                            Text("Add property")
                        }
                    }
                }
            }
        }

        Spacer(Modifier.height(12.dp))

        if (state.validationErrors.isNotEmpty()) {
            state.validationErrors.forEach {
                Text(it, color = MaterialTheme.colorScheme.error)
            }
        }

        Spacer(Modifier.weight(1f))

        Button(
            onClick = { onExport(true) },
            enabled = state.canExport
        ) {
            Text("Export (dry-run)")
        }
    }
}