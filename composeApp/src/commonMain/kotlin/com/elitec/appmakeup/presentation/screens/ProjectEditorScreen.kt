package com.elitec.appmakeup.presentation.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.elitec.appmakeup.presentation.screens.components.ProjectTreePreview
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
            Row(Modifier.fillMaxSize()) {
                Box(
                    modifier = Modifier.weight(1f).fillMaxSize()
                ) {
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
                        },
                        modifier = Modifier.fillMaxSize()
                    )
                }
                Box(modifier = Modifier.width(300.dp).fillMaxSize()) {
                    this@Row.AnimatedVisibility(
                        visible = state.project != null,
                        modifier = Modifier.fillMaxSize()
                    ) {
                        Surface(
                            modifier = Modifier
                                .width(340.dp)
                                .fillMaxSize()
                                .padding(8.dp),
                            shape = RoundedCornerShape(15.dp),
                            tonalElevation = 4.dp
                        ) {
                            Column(Modifier.padding(12.dp)) {

                                Text(
                                    "Estructura de archivos",
                                    style = MaterialTheme.typography.titleMedium
                                )

                                Spacer(Modifier.height(8.dp))

                                state.project?.let { project ->
                                    ProjectTreePreview(
                                        project = project,
                                        modifier = Modifier.fillMaxSize()
                                    )
                                }

                            }
                        }
                    }
                }
                /*ProjectEditorContent(
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
                    },
                    modifier = Modifier.weight(7f).fillMaxSize()
                )*/
                /*AnimatedVisibility(
                    visible = state.project != null,
                    modifier = Modifier.weight(3f).fillMaxSize()
                ) {
                    Surface(
                        modifier = Modifier
                            .width(340.dp)
                            .fillMaxSize()
                            .padding(8.dp),
                        shape = RoundedCornerShape(15.dp),
                        tonalElevation = 4.dp
                    ) {
                        Column(Modifier.padding(12.dp)) {

                            Text(
                                "Estructura de archivos",
                                style = MaterialTheme.typography.titleMedium
                            )

                            Spacer(Modifier.height(8.dp))

                            state.project?.let { project ->
                                ProjectTreePreview(
                                    project = project,
                                    modifier = Modifier.fillMaxSize()
                                )
                            }

                        }
                    }
                }*/
            }
        }
    }
}