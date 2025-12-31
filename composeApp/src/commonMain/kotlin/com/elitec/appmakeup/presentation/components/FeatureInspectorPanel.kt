package com.elitec.appmakeup.presentation.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.elitec.appmakeup.domain.modeling.Feature
import com.elitec.appmakeup.domain.modeling.entity.EntityProperty
import com.elitec.appmakeup.domain.modeling.entity.PropertyType

@Composable
fun FeatureInspectorPanel(
    feature: Feature?,
    onAddProperty: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp)
    ) {
        if (feature == null) {
            Text("Select a Feature")
            return
        }

        Text(
            text = "Feature: ${feature.name}",
            style = MaterialTheme.typography.titleLarge
        )

        Spacer(Modifier.height(16.dp))

        Text("Properties", style = MaterialTheme.typography.titleMedium)

        Spacer(Modifier.height(8.dp))

        if (feature.entity.properties.isEmpty()) {
            Text("No properties yet")
        }

        feature.entity.properties.forEach { prop ->
            Text("- ${prop.name}: ${prop.type}")
        }

        Spacer(Modifier.height(16.dp))

        Button(onClick = onAddProperty) {
            Text("Add Property")
        }
    }
}