package com.elitec.appmakeup.presentation.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun ExportScreen(
    projectPath: String,
    onDone: () -> Unit
) {
    var isGenerating by remember { mutableStateOf(true) }
    var error by remember { mutableStateOf<String?>(null) }

    LaunchedEffect(null) {
        try {
            // aquí llamas a GenerateCodeUseCase vía ViewModel
            // exportViewModel.generate(projectPath)
            isGenerating = false
        } catch (e: Exception) {
            error = e.message
            isGenerating = false
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center
    ) {
        when {
            isGenerating -> {
                CircularProgressIndicator()
                Spacer(Modifier.height(16.dp))
                Text("Generando código…")
            }
            error != null -> {
                Text(error!!, color = MaterialTheme.colorScheme.error)
                Button(onClick = onDone) {
                    Text("Volver")
                }
            }
            else -> {
                Text("Código generado correctamente 🎉")
                Spacer(Modifier.height(16.dp))
                Button(onClick = onDone) {
                    Text("Volver al editor")
                }
            }
        }
    }
}