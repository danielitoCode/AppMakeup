package com.elitec.appmakeup.presentation.screens.expanded

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Divider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.elitec.appmakeup.domain.modeling.Feature
import com.elitec.appmakeup.domain.modeling.entity.DomainEntity
import com.elitec.appmakeup.domain.project.Project
import com.elitec.appmakeup.presentation.components.FeatureInspectorPanel
import com.elitec.appmakeup.presentation.components.FeatureListPanel
import com.elitec.appmakeup.presentation.util.defaultProject
import com.elitec.appmakeup.presentation.util.defaultProjectLocation
import com.elitec.appmakeup.presentation.viewmodels.ModelingViewModel
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun ModelingScreen(
    modifier: Modifier = Modifier,
    viewModel: ModelingViewModel = koinViewModel(),
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    // 🔑 Inicialización automática (V1)
    LaunchedEffect(null) {
        if (state.project == null) {
            viewModel.createProject(
                project = defaultProject(),
                location = defaultProjectLocation()
            )
        }
    }

    Row(modifier = Modifier.fillMaxSize()) {

        // -------------------------
        // Left panel - Features
        // -------------------------
        FeatureListPanel(
            features = state.project?.features.orEmpty(),
            onAddFeature = {
                viewModel.addFeature(
                    Feature(
                        name = "feature${state.project?.features?.size ?: 0}",
                        entity = DomainEntity(
                            name = "Entity",
                            properties = emptyList()
                        )
                    )
                )
            }
        )

        Divider(modifier = Modifier.fillMaxHeight().width(1.dp))

        // -------------------------
        // Right panel - Inspector
        // -------------------------
        FeatureInspectorPanel(
            feature = state.project?.features?.firstOrNull(),
            onAddProperty = { property ->
                viewModel.addProperty(
                    featureName = state.project!!.features.first().name,
                    property = property
                )
            }
        )
    }
}