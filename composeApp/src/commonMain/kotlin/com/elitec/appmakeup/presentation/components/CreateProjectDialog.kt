package com.elitec.appmakeup.presentation.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.elitec.appmakeup.presentation.util.CreateProjectConfig
import com.elitec.appmakeup.presentation.util.pickDirectory

@Composable
fun CreateProjectDialog(
    onDismiss: () -> Unit,
    onCreate: (CreateProjectConfig) -> Unit
) {
    var appName by remember { mutableStateOf("") }
    var packageName by remember { mutableStateOf("") }
    var workspacePath by remember { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Create New Project") },
        text = {
            Column {

                OutlinedTextField(
                    modifier = Modifier.width(350.dp),
                    value = appName,
                    onValueChange = {
                        appName = it
                        if (packageName.isBlank()) {
                            packageName = "com.example.${it.lowercase()}"
                        }
                    },
                    label = { Text("Application Name") },
                    singleLine = true
                )

                Spacer(Modifier.height(12.dp))

                OutlinedTextField(
                    modifier = Modifier.width(350.dp),
                    value = packageName,
                    onValueChange = { packageName = it },
                    label = { Text("Package Name") },
                    singleLine = true
                )

                Spacer(Modifier.height(12.dp))

                OutlinedTextField(

                    value = workspacePath,
                    onValueChange = {},
                    label = { Text("Location") },
                    modifier = Modifier.width(350.dp),
                    enabled = false,
                    trailingIcon = {
                        TextButton(
                            onClick = {
                                workspacePath = pickDirectory() ?: workspacePath
                            }
                        ) {
                            Text("Browse")
                        }
                    }
                )

            }
        },
        confirmButton = {
            Button(
                enabled = appName.isNotBlank()
                        && packageName.isNotBlank()
                        && workspacePath.isNotBlank(),
                onClick = {
                    onCreate(
                        CreateProjectConfig(
                            appName = appName,
                            packageName = packageName,
                            workspacePath = workspacePath
                        )
                    )
                }
            ) {
                Text("Create")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    )
}