package com.elitec.appmakeup.presentation.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.elitec.appmakeup.presentation.screens.components.GenerationPlanPreview
import com.elitec.appmakeup.presentation.screens.components.PreviewFilesList
import com.elitec.appmakeup.presentation.viewmodels.ExportViewModel
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun ExportScreen(
    projectPath: String,
    onBack: () -> Unit,
    viewModel: ExportViewModel = koinViewModel()
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()
    val previewState by viewModel.uiPreviewState.collectAsStateWithLifecycle()

    LaunchedEffect(projectPath) {
        viewModel.loadProject(projectPath)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {

        /* ---------------------------
         * Header
         * --------------------------- */

        Text(
            text = "Export project",
            style = MaterialTheme.typography.headlineSmall
        )

        state.project?.let { project ->
            Text("Project: ${project.name}")
            Text("Package: ${project.packageName}")
            Text("Features: ${project.features.size}")
        }

        Divider()

        /* ---------------------------
         * Options
         * --------------------------- */

        Row(verticalAlignment = Alignment.CenterVertically) {
            Checkbox(
                checked = state.dryRun,
                onCheckedChange = viewModel::toggleDryRun
            )
            Spacer(Modifier.width(8.dp))
            Text("Dry-run (do not write files)")
        }

        Divider()

        /* ---------------------------
         * Preview: Generation plan
         * --------------------------- */

        Text(
            text = "Generation plan",
            style = MaterialTheme.typography.titleMedium
        )

        GenerationPlanPreview(previewState)

        Divider()

        /* ---------------------------
         * Preview: Files (dry-run)
         * --------------------------- */

        if (previewState.previewFiles.isNotEmpty()) {
            Text(
                text = "Files preview",
                style = MaterialTheme.typography.titleMedium
            )

            PreviewFilesList(previewState.previewFiles)
        }

        Spacer(Modifier.height(8.dp))

        /* ---------------------------
         * Actions
         * --------------------------- */

        Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {

            Button(
                onClick = viewModel::runPreview,
                enabled = state.project != null && !state.isRunning
            ) {
                Text("Preview")
            }

            Button(
                onClick = viewModel::runExport,
                enabled = state.project != null && !state.isRunning
            ) {
                Text(if (state.dryRun) "Run dry-run" else "Export")
            }
        }

        if (state.isRunning) {
            CircularProgressIndicator()
        }

        state.result?.let {
            Text(it, color = MaterialTheme.colorScheme.primary)
        }

        state.error?.let {
            Text(it, color = MaterialTheme.colorScheme.error)
        }

        Spacer(Modifier.weight(1f))

        OutlinedButton(onClick = onBack) {
            Text("Back")
        }
    }
}