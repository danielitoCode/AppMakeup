package com.elitec.appmakeup.presentation.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.elitec.appmakeup.presentation.viewmodels.ProjectEditorViewModel
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun ProjectEditorScreen(
    projectPath: String,
    onExport: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: ProjectEditorViewModel = koinViewModel()
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.Start
    ) {

        Text(
            text = state.project?.name ?: "Proyecto",
            style = MaterialTheme.typography.headlineSmall
        )

        Spacer(Modifier.height(16.dp))

        Row(
            modifier = Modifier.fillMaxSize(),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {

            // Features
            Column(modifier = Modifier.weight(1f)) {
                Text("Features", style = MaterialTheme.typography.titleMedium)

                state.features.forEach {
                    Text(
                        text = it.name,
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { viewModel.selectFeature(it.name) }
                            .padding(8.dp)
                    )
                }

                Button(
                    onClick = { viewModel.addFeature("NewFeature") },
                    modifier = Modifier.padding(top = 8.dp)
                ) {
                    Text("Agregar feature")
                }
            }

            // Properties
            Column(modifier = Modifier.weight(1f)) {
                Text("Propiedades", style = MaterialTheme.typography.titleMedium)

                state.properties.forEach {
                    Text(
                        text = "${it.name}: ${it.type}",
                        modifier = Modifier.padding(8.dp)
                    )
                }

                state.selectedFeature?.let {
                    Button(
                        onClick = {
                            viewModel.addProperty(
                                featureName = it.name,
                                propertyName = "newProperty",
                                type = "String"
                            )
                        }
                    ) {
                        Text("Agregar propiedad")
                    }
                }
            }
        }

        Spacer(Modifier.height(16.dp))

        Button(
            onClick = viewModel::generateCode,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Generar código")
        }

        state.error?.let {
            Text(it, color = MaterialTheme.colorScheme.error)
        }
    }
}