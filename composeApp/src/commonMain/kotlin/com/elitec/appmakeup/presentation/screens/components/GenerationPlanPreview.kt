package com.elitec.appmakeup.presentation.screens.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.elitec.appmakeup.presentation.states.GenerationPreviewUiState

@Composable
fun GenerationPlanPreview(state: GenerationPreviewUiState) {
    Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
        PlanItem("Domain layer", state.generateDomain)
        PlanItem("Data layer", state.generateData)
        PlanItem("Repositories", state.generateRepositories)
        PlanItem("Mappers", state.generateMappers)
    }
}

@Composable
private fun PlanItem(label: String, enabled: Boolean) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Icon(
            imageVector =
                if (enabled) Icons.Default.Check else Icons.Default.Close,
            contentDescription = null,
            tint =
                if (enabled)
                    MaterialTheme.colorScheme.primary
                else
                    MaterialTheme.colorScheme.onSurfaceVariant
        )
        Spacer(Modifier.width(8.dp))
        Text(label)
    }
}