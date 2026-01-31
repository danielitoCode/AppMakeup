package com.elitec.appmakeup.presentation.screens.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Divider
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.elitec.appmakeup.presentation.states.ProjectEditorUiState
import com.elitec.appmakeup.projects.model.AppProperty

@Composable
fun ProjectEditorContent(
    state: ProjectEditorUiState,
    onSelectFeature: (String) -> Unit,
    onAddFeature: (String) -> Unit,
    onAddEntity: (String, String) -> Unit,
    onExport: () -> Unit
) {
    Row(Modifier.fillMaxSize()) {

        // 🔹 Left panel – Features
        FeatureListPanel(
            features = state.features,
            selectedFeature = state.selectedFeature,
            onSelectFeature = onSelectFeature,
            onAddFeature = onAddFeature,
            modifier = Modifier.weight(0.3f)
        )

        Divider(Modifier.fillMaxHeight().width(1.dp))

        // 🔹 Middle panel – Entities
        EntityListPanel(
            feature = state.selectedFeature,
            onAddEntity = onAddEntity,
            modifier = Modifier.weight(0.3f)
        )

        Divider(Modifier.fillMaxHeight().width(1.dp))

        // 🔹 Right panel – Context (vacío por ahora)
        Box(
            modifier = Modifier.weight(0.4f).padding(16.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "Select an entity to edit",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}