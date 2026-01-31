package com.elitec.appmakeup.presentation.screens.components.editor

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
import com.elitec.appmakeup.projects.model.AppFeature

@Composable
fun FeatureListPanel(
    features: List<AppFeature>,
    selectedFeature: AppFeature?,
    onSelectFeature: (String) -> Unit,
    onAddFeature: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier.padding(16.dp)) {

        Text("Features", style = MaterialTheme.typography.titleMedium)

        Spacer(Modifier.height(8.dp))

        features.forEach { feature ->
            val selected = feature == selectedFeature

            Text(
                text = feature.name,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onSelectFeature(feature.name) }
                    .padding(8.dp)
                    .background(
                        if (selected)
                            MaterialTheme.colorScheme.primary.copy(alpha = 0.15f)
                        else Color.Transparent
                    ),
                fontWeight = if (selected) FontWeight.Bold else FontWeight.Normal
            )
        }

        Spacer(Modifier.height(16.dp))

        var newFeature by remember { mutableStateOf("") }

        OutlinedTextField(
            value = newFeature,
            onValueChange = { newFeature = it },
            label = { Text("New feature") },
            modifier = Modifier.fillMaxWidth()
        )

        Button(
            onClick = {
                if (newFeature.isNotBlank()) {
                    onAddFeature(newFeature.trim())
                    newFeature = ""
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Add feature")
        }
    }
}