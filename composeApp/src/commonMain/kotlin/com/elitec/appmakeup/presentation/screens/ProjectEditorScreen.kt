package com.elitec.appmakeup.presentation.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
    onBack: () -> Unit,
    onExportFinish: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: ProjectEditorViewModel = koinViewModel()
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()

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
            ErrorView(
                message = state.error!!,
                onBack = onBack
            )
        }

        state.project != null -> {
            ProjectEditorContent(
                state = state,
                onAddFeature = viewModel::addFeature,
                onRemoveFeature = viewModel::removeFeature,
                onSelectFeature = viewModel::selectFeature,
                onAddProperty = viewModel::addProperty,
                onRemoveProperty = viewModel::removeProperty,
                onExport = {
                    viewModel.exportProject()
                    onExportFinish()
                }
            )
        }
    }
}