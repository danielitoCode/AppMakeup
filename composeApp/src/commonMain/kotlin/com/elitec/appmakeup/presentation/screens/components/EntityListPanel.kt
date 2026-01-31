package com.elitec.appmakeup.presentation.screens.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.elitec.appmakeup.projects.model.AppEntity
import com.elitec.appmakeup.projects.model.AppFeature

@Composable
fun EntityListPanel(
    feature: AppFeature?,
    selectedEntity: AppEntity?,
    onSelectEntity: (String) -> Unit,
    onAddEntity: (String, String) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier.padding(16.dp)) {

        Text("Entities", style = MaterialTheme.typography.titleMedium)

        Spacer(Modifier.height(8.dp))

        if (feature == null) {
            Text("Select a feature")
            return
        }

        feature.entities.forEach { entity ->
            val selected = entity == selectedEntity

            Text(
                text = entity.name,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onSelectEntity(entity.name) }
                    .background(
                        if (selected)
                            MaterialTheme.colorScheme.primary.copy(alpha = 0.15f)
                        else Color.Transparent
                    )
                    .padding(8.dp),
                fontWeight = if (selected) FontWeight.Bold else FontWeight.Normal
            )
        }

        Spacer(Modifier.height(16.dp))

        var newEntity by remember { mutableStateOf("") }

        OutlinedTextField(
            value = newEntity,
            onValueChange = { newEntity = it },
            label = { Text("New entity") }
        )

        Button(
            onClick = {
                if (newEntity.isNotBlank()) {
                    onAddEntity(feature.name, newEntity.trim())
                    newEntity = ""
                }
            }
        ) {
            Text("Add entity")
        }
    }
}