package com.elitec.appmakeup.presentation.screens.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment

@Composable
fun AddPropertyInput(
    featureName: String,
    onAddProperty: (String, String, String, Boolean) -> Unit
) {
    var name by remember { mutableStateOf("") }
    var type by remember { mutableStateOf("String") }
    var isId by remember { mutableStateOf(false) }

    Column {
        OutlinedTextField(
            value = name,
            onValueChange = { name = it },
            label = { Text("Property name") }
        )

        OutlinedTextField(
            value = type,
            onValueChange = { type = it },
            label = { Text("Type") }
        )

        Row(verticalAlignment = Alignment.CenterVertically) {
            Checkbox(checked = isId, onCheckedChange = { isId = it })
            Text("Identifier")
        }

        Button(
            onClick = {
                if (name.isNotBlank()) {
                    onAddProperty(featureName, name, type, isId)
                    name = ""
                    isId = false
                }
            }
        ) {
            Text("Add property")
        }
    }
}