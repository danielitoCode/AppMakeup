package com.elitec.appmakeup.presentation.screens.expanded

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import appmakeup.composeapp.generated.resources.Res
import appmakeup.composeapp.generated.resources.sinfoto
import appmakeup.composeapp.generated.resources.sinfotoicon
import com.elitec.appmakeup.domain.project.ProjectLocation
import com.elitec.appmakeup.presentation.components.CreateProjectDialog
import com.elitec.appmakeup.presentation.components.CreatingProjectOverlay
import com.elitec.appmakeup.presentation.components.RecentProjectsSection
import com.elitec.appmakeup.presentation.util.pickDirectory
import com.elitec.appmakeup.presentation.viewmodels.WelcomeViewModel
import okio.Path.Companion.toPath
import org.jetbrains.compose.resources.painterResource
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun WelcomeScreen(
    onToggleTheme: () -> Unit,
    onNavigateToModeling: (ProjectLocation, String) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: WelcomeViewModel = koinViewModel(),
    isWindowsApp: Boolean = true,
    isDarkTheme: Boolean = isSystemInDarkTheme(),
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val iconThemeButton by animateColorAsState(MaterialTheme.colorScheme.onBackground)

    var showCreateDialog by remember { mutableStateOf(false) }


    Box(
        contentAlignment = Alignment.TopStart,
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(24.dp)
    ) {
        // -------------------------
        // Top-start actions (Theme)
        // -------------------------
        if(!isWindowsApp) {
            IconButton(onClick = onToggleTheme) {
                AnimatedContent(
                    targetState = if (isDarkTheme)
                        Icons.Default.LightMode
                    else
                        Icons.Default.DarkMode,
                ) { icon ->
                    Icon(
                        imageVector = icon,
                        contentDescription = "Toggle theme",
                        tint = iconThemeButton
                    )
                }
            }
        }
        /* ToggleButton
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
                    contentDescription = "Toggle theme",
                    tint = MaterialTheme.colorScheme.onBackground
                )
            }
        }*/
        // -------------------------
        // Center content
        // -------------------------
        Row(
            modifier = Modifier.fillMaxSize()
        ) {
            Column(
                modifier = Modifier.weight(1f)
                    .fillMaxSize()
                    .padding(20.dp),
                verticalArrangement = Arrangement.SpaceBetween,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Text(
                    fontWeight = FontWeight.Bold,
                    text = "App Makeup",
                    color = MaterialTheme.colorScheme.onBackground,
                    style = MaterialTheme.typography.headlineMedium
                )

                Image(
                    painter = painterResource(Res.drawable.sinfotoicon),
                    contentDescription = null,
                    contentScale = ContentScale.FillWidth,
                    modifier = Modifier.fillMaxWidth()
                        .padding(start = 15.dp, end = 15.dp)
                        .clip(RoundedCornerShape(20.dp))
                )




                // -------------------------
                // Quick actions card
                // -------------------------
                Column {
                    Text(
                        text = "Create and model applications visually",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.7f)
                    )
                    Spacer(Modifier.height(16.dp))
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

                            OutlinedButton(
                                modifier = Modifier.fillMaxWidth(),
                                onClick = {
                                    val selectedDir = pickDirectory() ?: return@OutlinedButton

                                    val projectPath = selectedDir.toPath()
                                    val projectName = projectPath.name
                                    val workspacePath = projectPath.parent ?: return@OutlinedButton

                                    viewModel.openExistingProject(
                                        location = ProjectLocation(workspacePath.toString()),
                                        projectName = projectName,
                                        onSuccess = onNavigateToModeling
                                    )
                                }
                            ) {
                                Icon(
                                    imageVector = Icons.Default.FolderOpen,
                                    contentDescription = null,
                                    tint = MaterialTheme.colorScheme.onSurface,
                                )
                                Spacer(Modifier.width(8.dp))
                                Text(
                                    text = "Open Existing Project",
                                    color = MaterialTheme.colorScheme.onSurface,
                                )
                            }
                            Spacer(Modifier.height(12.dp))
                            Button(
                                modifier = Modifier.fillMaxWidth(),
                                onClick = { showCreateDialog = true }
                            ) {
                                Icon(Icons.Default.Add, contentDescription = null)
                                Spacer(Modifier.width(8.dp))
                                Text("Create New Project")
                            }
                        }
                    }
                }
            }
            // -------------------------
            // Recent projects
            // -------------------------

            Box(
                modifier = Modifier.weight(1f).fillMaxWidth()
            ) {
                this@Row.AnimatedVisibility(
                    visible = state.recentProjects.isNotEmpty()
                ) {
                    RecentProjectsSection(
                        modifier = Modifier.fillMaxWidth().padding(10.dp),
                        projects = state.recentProjects,
                        onOpenProject = { location ->

                            val projectPath = location.value.toPath()
                            val projectName = projectPath.name
                            val workspacePath = projectPath.parent

                            if (workspacePath != null) {
                                viewModel.openExistingProject(
                                    location = ProjectLocation(workspacePath.toString()),
                                    projectName = projectName,
                                    onSuccess = onNavigateToModeling
                                )
                            } else {
                                viewModel.clearError() // o setError si tienes
                            }
                        }
                    )
                }
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
                        onSuccess = { workspace, projectName ->
                            showCreateDialog = false
                            onNavigateToModeling(workspace, projectName)
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