package com.elitec.appmakeup.presentation.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.elitec.appmakeup.presentation.screens.components.editor.ProjectEditorContent
import com.elitec.appmakeup.presentation.viewmodels.ProjectEditorViewModel
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun ProjectEditorScreen(
    projectPath: String,
    onNavigateToExport: () -> Unit,
    viewModel: ProjectEditorViewModel = koinViewModel()
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(projectPath) {
        viewModel.loadProject(projectPath)
    }

    when {
        state.isLoading -> {
            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        }

        state.error != null -> {
            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text(
                    text = state.error ?: "Unknown error",
                    color = MaterialTheme.colorScheme.error
                )
            }
        }

        else -> {
            ProjectEditorContent(
                state = state,
                onSelectFeature = viewModel::selectFeature,
                onAddFeature = viewModel::addFeature,
                onAddEntity = viewModel::addEntity,
                onSelectEntity = viewModel::selectEntity,
                onAddProperty = viewModel::addEntityProperty,
                onRemoveProperty = viewModel::removeEntityProperty,
                onUpdateRepositoryContract = viewModel::updateRepositoryContract,
                onAddRelation = viewModel::addRelation,
                onDeleteRelation = viewModel::removeRelation,
                onExport = {
                    if (state.canExport) {
                        viewModel.export(dryRun = true)
                        onNavigateToExport()
                    }
                }
            )
        }
    }
}