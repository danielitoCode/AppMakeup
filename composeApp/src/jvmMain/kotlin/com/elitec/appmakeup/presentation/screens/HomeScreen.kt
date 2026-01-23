package com.elitec.appmakeup.presentation.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Divider
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.elitec.appmakeup.presentation.viewmodels.HomeViewModel
import org.koin.compose.viewmodel.koinViewModel

@Suppress("EffectKeys")
@Composable
fun HomeScreen(
    onProjectCreate: () -> Unit,
    onOpenProject: (String) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = koinViewModel()
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.loadRecentProjects()
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text("AppMakeup", style = MaterialTheme.typography.headlineMedium)

        Button(onClick = onProjectCreate) {
            Text("Nuevo proyecto")
        }

        Divider()

        Text("Proyectos recientes", style = MaterialTheme.typography.titleMedium)

        if (state.recentProjects.isEmpty()) {
            Text("No hay proyectos recientes")
        } else {
            state.recentProjects.forEach { path ->
                Text(
                    text = path,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { onOpenProject(path) }
                        .padding(8.dp)
                )
            }
        }

        state.error?.let {
            Text(it, color = MaterialTheme.colorScheme.error)
        }
    }
}