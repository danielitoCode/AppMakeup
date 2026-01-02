package com.elitec.appmakeup.presentation.screens.expanded

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Divider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.elitec.appmakeup.domain.modeling.Feature
import com.elitec.appmakeup.domain.modeling.entity.DomainEntity
import com.elitec.appmakeup.domain.modeling.entity.EntityProperty
import com.elitec.appmakeup.domain.modeling.entity.PropertyType
import com.elitec.appmakeup.domain.project.ProjectLocation
import com.elitec.appmakeup.presentation.components.FeatureInspectorPanel
import com.elitec.appmakeup.presentation.components.FeatureListPanel
import com.elitec.appmakeup.presentation.components.ModelingTopBar
import com.elitec.appmakeup.presentation.util.ModelingMode
import com.elitec.appmakeup.presentation.viewmodels.ModelingViewModel
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun ModelingScreen(
    projectLocation: ProjectLocation,
    mode: ModelingMode,
    onBack: () -> Unit,
    viewModel: ModelingViewModel = koinViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    // 🔑 INICIALIZACIÓN DEL VIEWMODEL
    LaunchedEffect(projectLocation, mode) {
        when (mode) {
            ModelingMode.CREATE -> {
                // el proyecto YA fue creado, solo cargar
                viewModel.initialize(projectLocation)
            }
            ModelingMode.OPEN -> {
                viewModel.initialize(projectLocation)
            }
        }
    }

    Column(modifier = Modifier.fillMaxSize()) {

        // -------------------------
        // Top Bar (IDE-style)
        // -------------------------
        ModelingTopBar(
            projectName = state.project?.name ?: "No Project",
            isDirty = state.isDirty,
            onSave = { viewModel.save() },
            onGenerate = { viewModel.generate() }
        )

        Divider()

        // -------------------------
        // Main content
        // -------------------------
        val selectedFeature =
            state.project?.features
                ?.firstOrNull { it.name == state.selectedFeatureName }

        Row(modifier = Modifier.fillMaxSize()) {

            FeatureListPanel(
                features = state.project?.features.orEmpty(),
                selectedFeatureName = state.selectedFeatureName,
                onSelectFeature = { viewModel.selectFeature(it) },
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

            Divider(
                modifier = Modifier
                    .fillMaxHeight()
                    .width(1.dp)
            )

            FeatureInspectorPanel(
                feature = selectedFeature,
                onAddProperty = {
                    viewModel.addProperty(
                        EntityProperty(
                            name = "property${selectedFeature?.entity?.properties?.size ?: 0}",
                            type = PropertyType.StringType
                        )
                    )
                }
            )
        }
    }
}