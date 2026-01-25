package com.elitec.appmakeup.presentation.screens.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun AddFeatureInput(
    onAddFeature: (String) -> Unit
) {
    var name by remember { mutableStateOf("") }

    Row {
        OutlinedTextField(
            value = name,
            onValueChange = { name = it },
            label = { Text("New feature") }
        )
        Spacer(Modifier.width(8.dp))
        Button(onClick = {
            if (name.isNotBlank()) {
                onAddFeature(name)
                name = ""
            }
        }) {
            Text("Add")
        }
    }
}
