package com.elitec.appmakeup.presentation.screens.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.elitec.appmakeup.projects.model.AppFeature

@Composable
fun EntityListPanel(
    feature: AppFeature?,
    onAddEntity: (String, String) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier.padding(16.dp)) {

        Text("Entities", style = MaterialTheme.typography.titleMedium)

        Spacer(Modifier.height(8.dp))

        if (feature == null) {
            Text(
                "Select a feature",
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            return
        }

        feature.entities.forEach { entity ->
            Text(
                text = entity.name,
                modifier = Modifier.padding(8.dp)
            )
        }

        Spacer(Modifier.height(16.dp))

        var newEntity by remember { mutableStateOf("") }

        OutlinedTextField(
            value = newEntity,
            onValueChange = { newEntity = it },
            label = { Text("New entity") },
            modifier = Modifier.fillMaxWidth()
        )

        Button(
            onClick = {
                if (newEntity.isNotBlank()) {
                    onAddEntity(feature.name, newEntity.trim())
                    newEntity = ""
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Add entity")
        }
    }
}