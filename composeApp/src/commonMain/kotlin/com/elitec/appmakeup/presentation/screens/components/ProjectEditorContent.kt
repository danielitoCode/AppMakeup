package com.elitec.appmakeup.presentation.screens.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.elitec.appmakeup.presentation.states.ProjectEditorUiState

@Composable
fun ProjectEditorContent(
    state: ProjectEditorUiState,
    onAddFeature: (String) -> Unit,
    onRemoveFeature: (String) -> Unit,
    onSelectFeature: (String) -> Unit,
    onAddProperty: (String, String, String, Boolean) -> Unit,
    onRemoveProperty: (String, String) -> Unit,
    onExport: () -> Unit
) {
    Row(modifier = Modifier.fillMaxSize().padding(16.dp)) {

        /* ---------- Feature list ---------- */
        Column(
            modifier = Modifier.weight(0.3f)
        ) {
            Text("Features", style = MaterialTheme.typography.titleMedium)

            Spacer(Modifier.height(8.dp))

            state.features.forEach { feature ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { onSelectFeature(feature.name) }
                        .padding(8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(feature.name)
                    IconButton(onClick = { onRemoveFeature(feature.name) }) {
                        Icon(Icons.Default.Delete, contentDescription = null)
                    }
                }
            }

            Spacer(Modifier.height(8.dp))

            AddFeatureInput(onAddFeature)
        }

        Spacer(Modifier.width(16.dp))

        /* ---------- Feature editor ---------- */
        Column(
            modifier = Modifier.weight(0.7f)
        ) {
            val feature = state.selectedFeature

            if (feature == null) {
                Text("Select a feature to edit")
                return@Column
            }

            Text(
                "Feature: ${feature.name}",
                style = MaterialTheme.typography.titleMedium
            )

            Spacer(Modifier.height(8.dp))

            Text("Properties")

            Spacer(Modifier.height(4.dp))

            state.properties.forEach { prop ->
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text("${prop.name}: ${prop.type}")
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

            AddPropertyInput(
                featureName = feature.name,
                onAddProperty = onAddProperty
            )

            Spacer(Modifier.height(24.dp))

            Button(
                onClick = onExport,
                enabled = !state.isExporting
            ) {
                Text(if (state.isExporting) "Exporting..." else "Export Project")
            }
        }
    }
}