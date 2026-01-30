package com.elitec.appmakeup.presentation.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.elitec.appmakeup.presentation.screens.components.ErrorView
import com.elitec.appmakeup.presentation.screens.components.ProjectEditorContent
import com.elitec.appmakeup.presentation.viewmodels.ProjectEditorViewModel
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun ProjectEditorScreen(
    projectPath: String,
    onNavigateToExport: () -> Unit,
    viewModel: ProjectEditorViewModel = koinViewModel()
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()

    /* ----------------------------------------------------
     * Load project once
     * ---------------------------------------------------- */
    LaunchedEffect(projectPath) {
        viewModel.loadProject(projectPath)
    }

    when {
        state.isLoading -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }

        state.error != null -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = state.error ?: "Unknown error",
                    color = MaterialTheme.colorScheme.error
                )
            }
        }

        state.project != null -> {
            ProjectEditorContent(
                state = state,
                onSelectFeature = viewModel::selectFeature,
                onAddFeature = viewModel::addFeature,
                onRemoveFeature = viewModel::removeFeature,
                onAddProperty = { feature, entity, property ->
                    viewModel.addEntityProperty(feature, entity, property)
                },
                onRemoveProperty = { feature, entity, property ->
                    viewModel.removeEntityProperty(feature, entity, property)
                },
                onExport = { dryRun ->
                    viewModel.export(dryRun)
                    if (state.canExport) {
                        onNavigateToExport()
                    }
                }
            )
        }

        else -> {
            // Estado imposible, pero seguro
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text("No project loaded")
            }
        }
    }
}