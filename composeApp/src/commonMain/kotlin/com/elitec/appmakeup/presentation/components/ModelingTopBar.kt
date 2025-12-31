package com.elitec.appmakeup.presentation.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun ModelingTopBar(
    projectName: String,
    isDirty: Boolean,
    onSave: () -> Unit,
    onGenerate: () -> Unit
) {
    Surface(
        tonalElevation = 2.dp
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp)
                .padding(horizontal = 12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            // Project name + dirty indicator
            Text(
                text = buildString {
                    append(projectName)
                    if (isDirty) append(" *")
                },
                style = MaterialTheme.typography.titleMedium
            )

            Spacer(modifier = Modifier.weight(1f))

            // Actions
            TextButton(onClick = onSave) {
                Text("Save")
            }

            Spacer(modifier = Modifier.width(8.dp))

            Button(onClick = onGenerate) {
                Text("Generate")
            }
        }
    }
}