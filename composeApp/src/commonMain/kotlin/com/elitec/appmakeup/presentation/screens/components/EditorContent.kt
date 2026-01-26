package com.elitec.appmakeup.presentation.screens.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.elitec.appmakeup.presentation.states.ProjectEditorUiState

@Composable
fun EditorContent(
    state: ProjectEditorUiState,
    onSelectFeature: (String) -> Unit,
    onAddFeature: (String) -> Unit,
    onRemoveFeature: (String) -> Unit,
    onAddProperty: (String, String, String, Boolean) -> Unit,
    onRemoveProperty: (String, String) -> Unit,
    onExport: () -> Unit
) {
    Row(modifier = Modifier.fillMaxSize().padding(16.dp)) {

        /* ---------------------------
         * Features
         * --------------------------- */
        Column(modifier = Modifier.weight(0.3f)) {
            Text("Features", style = MaterialTheme.typography.titleMedium)

            state.features.forEach { feature ->
                Row(
                    modifier = Modifier.fillMaxWidth()
                        .clickable { onSelectFeature(feature.name) }
                        .padding(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(feature.name, modifier = Modifier.weight(1f))
                    IconButton(onClick = { onRemoveFeature(feature.name) }) {
                        Icon(Icons.Default.Delete, contentDescription = null)
                    }
                }
            }

            Spacer(Modifier.height(8.dp))
            AddFeatureInput(onAddFeature)
        }

        Spacer(Modifier.width(16.dp))

        /* ---------------------------
         * Feature detail
         * --------------------------- */
        Column(modifier = Modifier.weight(0.7f)) {
            state.selectedFeature?.let { feature ->
                Text(
                    "Feature: ${feature.name}",
                    style = MaterialTheme.typography.titleMedium
                )

                feature.properties.forEach { prop ->
                    Row(
                        modifier = Modifier.fillMaxWidth().padding(8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            "${prop.name}: ${prop.type}" +
                                    if (prop.isIdentifier) " (ID)" else "",
                            modifier = Modifier.weight(1f)
                        )

                        IconButton(
                            onClick = {
                                onRemoveProperty(feature.name, prop.name)
                            }
                        ) {
                            Icon(Icons.Default.Delete, contentDescription = null)
                        }
                    }
                }

                Spacer(Modifier.height(8.dp))
                AddPropertyInput(feature.name, onAddProperty)
            }

            Spacer(Modifier.height(16.dp))

            /* ---------------------------
             * Validation
             * --------------------------- */
            if (state.validationErrors.isNotEmpty()) {
                state.validationErrors.forEach {
                    Text(it, color = MaterialTheme.colorScheme.error)
                }
            }

            Spacer(Modifier.height(16.dp))

            Button(
                onClick = onExport,
                enabled = state.canExport
            ) {
                Text("Export project")
            }
        }
    }
}