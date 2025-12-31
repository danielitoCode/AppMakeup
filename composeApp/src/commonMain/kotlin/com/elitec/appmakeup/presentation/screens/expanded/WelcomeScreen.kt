package com.elitec.appmakeup.presentation.screens.expanded

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.filled.FolderOpen
import androidx.compose.material.icons.filled.LightMode
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.elitec.appmakeup.domain.project.Project
import com.elitec.appmakeup.domain.project.ProjectLocation
import com.elitec.appmakeup.presentation.components.CreateProjectDialog
import com.elitec.appmakeup.presentation.components.CreatingProjectOverlay
import com.elitec.appmakeup.presentation.components.RecentProjectItem
import com.elitec.appmakeup.presentation.components.RecentProjectsSection
import com.elitec.appmakeup.presentation.util.createProjectFromConfig
import com.elitec.appmakeup.presentation.util.pickDirectory
import com.elitec.appmakeup.presentation.viewmodels.WelcomeViewModel
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun WelcomeScreen(
    viewModel: WelcomeViewModel = koinViewModel(),
    isDarkTheme: Boolean,
    onToggleTheme: () -> Unit,
    onNavigateToModeling: (ProjectLocation) -> Unit,
    onOpenProject: (ProjectLocation) -> Unit
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    var showCreateDialog by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(24.dp)
    ) {

        // -------------------------
        // Top-right actions (Theme)
        // -------------------------
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.End
        ) {
            IconButton(onClick = onToggleTheme) {
                Icon(
                    imageVector = if (isDarkTheme)
                        Icons.Default.LightMode
                    else
                        Icons.Default.DarkMode,
                    contentDescription = "Toggle theme"
                )
            }
        }

        // -------------------------
        // Center content
        // -------------------------
        Column(
            modifier = Modifier
                .align(Alignment.Center)
                .width(420.dp)
        ) {

            Text(
                text = "App Makeup",
                style = MaterialTheme.typography.headlineMedium
            )

            Spacer(Modifier.height(6.dp))

            Text(
                text = "Create and model applications visually",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.7f)
            )

            Spacer(Modifier.height(32.dp))

            // -------------------------
            // Quick actions card
            // -------------------------
            Surface(
                shape = RoundedCornerShape(8.dp),
                tonalElevation = 2.dp,
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(16.dp)) {

                    Text(
                        text = "Quick Start",
                        style = MaterialTheme.typography.titleMedium
                    )

                    Spacer(Modifier.height(16.dp))

                    Button(
                        modifier = Modifier.fillMaxWidth(),
                        onClick = { showCreateDialog = true }
                    ) {
                        Icon(Icons.Default.Add, contentDescription = null)
                        Spacer(Modifier.width(8.dp))
                        Text("Create New Project")
                    }

                    Spacer(Modifier.height(12.dp))

                    OutlinedButton(
                        modifier = Modifier.fillMaxWidth(),
                        onClick = {
                            val directory = pickDirectory() ?: return@OutlinedButton
                            onOpenProject(ProjectLocation(directory))
                        }
                    ) {
                        Icon(Icons.Default.FolderOpen, contentDescription = null)
                        Spacer(Modifier.width(8.dp))
                        Text("Open Existing Project")
                    }
                }
            }

            // -------------------------
            // Recent projects
            // -------------------------
            if (state.recentProjects.isNotEmpty()) {
                Spacer(Modifier.height(32.dp))

                RecentProjectsSection(
                    projects = state.recentProjects,
                    onOpenProject = { location ->
                        viewModel.openExistingProject(
                            location = location,
                            onSuccess = onNavigateToModeling
                        )
                    }
                )
            }
        }

        // -------------------------
        // Create Project Dialog
        // -------------------------
        if (showCreateDialog) {
            CreateProjectDialog(
                onDismiss = { showCreateDialog = false },
                onCreate = { config ->
                    viewModel.createNewProject(
                        config = config,
                        onSuccess = { location ->
                            showCreateDialog = false
                            onNavigateToModeling(location)
                        }
                    )
                }
            )
        }

        if (state.isCreatingProject) {
            CreatingProjectOverlay()
        }

        state.error?.let { error ->
            AlertDialog(
                onDismissRequest = { viewModel.clearError() },
                title = { Text("Error") },
                text = { Text(error) },
                confirmButton = {
                    TextButton(onClick = { viewModel.clearError() }) {
                        Text("OK")
                    }
                }
            )
        }
    }
}