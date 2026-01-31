package com.elitec.appmakeup.presentation.screens.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun ValidationPanel(errors: List<String>) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.errorContainer)
            .padding(16.dp)
    ) {
        Text(
            "Validation errors",
            color = MaterialTheme.colorScheme.error,
            style = MaterialTheme.typography.titleSmall
        )

        errors.forEach {
            Text("• $it", color = MaterialTheme.colorScheme.error)
        }
    }
}