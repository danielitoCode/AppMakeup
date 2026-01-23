package com.elitec.appmakeup.presentation.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.elitec.appmakeup.presentation.util.pickDirectory
import com.elitec.appmakeup.presentation.viewmodels.CreateProjectViewModel
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun CreateProjectScreen(
    onProjectCreate: (String) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: CreateProjectViewModel = koinViewModel()
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()

    if (state.isCreated) {
        onProjectCreate(state.path)
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text("Crear nuevo proyecto", style = MaterialTheme.typography.headlineSmall)

        OutlinedTextField(
            value = state.name,
            onValueChange = viewModel::onNameChange,
            label = { Text("Nombre del proyecto") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = state.packageName,
            onValueChange = viewModel::onPackageChange,
            label = { Text("Package base") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = state.path,
            onValueChange = viewModel::onPathChange,
            label = { Text("Ruta del proyecto") },
            modifier = Modifier.fillMaxWidth(),
            trailingIcon = {
                Button(
                    onClick = {
                        pickDirectory()?.let { viewModel.onPathChange(it) }
                    }
                ) {
                    Text("Elegir")
                }
            }
        )

        state.error?.let {
            Text(it, color = MaterialTheme.colorScheme.error)
        }

        Button(
            onClick = viewModel::createProject,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Crear proyecto")
        }
    }
}