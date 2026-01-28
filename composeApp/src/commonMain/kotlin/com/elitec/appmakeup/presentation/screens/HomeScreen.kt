package com.elitec.appmakeup.presentation.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.elitec.appmakeup.presentation.viewmodels.HomeViewModel
import org.koin.compose.viewmodel.koinViewModel

@OptIn(ExperimentalSharedTransitionApi::class)
@Suppress("EffectKeys")
@Composable
fun HomeScreen(
    sharedTransitionScope: SharedTransitionScope,
    onProjectCreate: () -> Unit,
    onOpenProject: (String) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = koinViewModel()
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()
    val lazyColumnState = rememberLazyListState()

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

        AnimatedVisibility(
            visible = state.recentProjects.isEmpty()
        ) {
            Row(
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("No hay proyectos recientes")
            }
        }

        AnimatedVisibility(
            visible = state.recentProjects.isNotEmpty()
        ) {
            Surface(
                color = MaterialTheme.colorScheme.surface,
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(10.dp)
            ) {
                LazyColumn {
                    items(state.recentProjects) { path ->
                        Text(
                            text = path,
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable { onOpenProject(path) }
                                .padding(8.dp)
                        )
                    }
                }
            }
        }

        state.error?.let {
            Text(it, color = MaterialTheme.colorScheme.error)
        }
    }
}