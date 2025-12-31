package com.elitec.appmakeup.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.elitec.appmakeup.domain.modeling.Feature

@Composable
fun FeatureListPanel(
    features: List<Feature>,
    selectedFeatureName: String?,
    onSelectFeature: (String) -> Unit,
    onAddFeature: () -> Unit
) {
    Column(
        modifier = Modifier.width(250.dp).fillMaxHeight().padding(8.dp)
    ) {
        Text("Features", style = MaterialTheme.typography.titleMedium)

        Spacer(Modifier.height(8.dp))

        LazyColumn(modifier = Modifier.weight(1f)) {
            items(features) { feature ->
                val isSelected = feature.name == selectedFeatureName

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp)
                        .background(
                            if (isSelected)
                                MaterialTheme.colorScheme.primary.copy(alpha = 0.15f)
                            else
                                MaterialTheme.colorScheme.surfaceVariant
                        )
                        .clickable {
                            onSelectFeature(feature.name)
                        }
                        .padding(8.dp)
                ) {
                    Text(feature.name)
                }
            }
        }

        Button(
            onClick = onAddFeature,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Add Feature")
        }
    }
}