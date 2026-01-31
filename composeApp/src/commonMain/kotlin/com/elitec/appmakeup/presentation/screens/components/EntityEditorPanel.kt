package com.elitec.appmakeup.presentation.screens.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.elitec.appmakeup.projects.model.AppEntity
import com.elitec.appmakeup.projects.model.AppFeature
import com.elitec.appmakeup.projects.model.AppProperty

@Composable
fun EntityEditorPanel(
    feature: AppFeature?,
    entity: AppEntity?,
    onAddProperty: (String, String, AppProperty) -> Unit,
    onRemoveProperty: (String, String, String) -> Unit
) {
    if (feature == null || entity == null) {
        Text("Select an entity", color = MaterialTheme.colorScheme.onSurfaceVariant)
        return
    }

    Column {
        Text("Entity: ${entity.name}", style = MaterialTheme.typography.titleMedium)

        Spacer(Modifier.height(8.dp))

        entity.properties.forEach { prop ->
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("${prop.name}: ${prop.type}")
                if (prop.isIdentifier) {
                    Spacer(Modifier.width(8.dp))
                    Text("(ID)", color = MaterialTheme.colorScheme.primary)
                }

                Spacer(Modifier.weight(1f))

                IconButton(onClick = {
                    onRemoveProperty(feature.name, entity.name, prop.name)
                }) {
                    Icon(Icons.Default.Delete, contentDescription = null)
                }
            }
        }

        Spacer(Modifier.height(12.dp))

        var name by remember { mutableStateOf("") }
        var type by remember { mutableStateOf("String") }

        OutlinedTextField(name, { name = it }, label = { Text("Property name") })
        OutlinedTextField(type, { type = it }, label = { Text("Type") })

        Button(
            onClick = {
                onAddProperty(
                    feature.name,
                    entity.name,
                    AppProperty(name, type, isIdentifier = false)
                )
                name = ""
            }
        ) {
            Text("Add property")
        }
    }
}