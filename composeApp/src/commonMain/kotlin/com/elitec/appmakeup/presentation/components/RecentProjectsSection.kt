package com.elitec.appmakeup.presentation.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.elitec.appmakeup.domain.project.ProjectLocation

@Composable
fun RecentProjectsSection(
    projects: List<ProjectLocation>,
    onOpenProject: (ProjectLocation) -> Unit
) {
    Column {
        Text("Recent Projects")

        if (projects.isEmpty()) {
            Text(
                text = "No recent projects",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.6f)
            )
        } else {
            projects.forEach { location ->
                RecentProjectItem(
                    location = location,
                    onClick = { onOpenProject(location) }
                )
            }
        }
    }
}