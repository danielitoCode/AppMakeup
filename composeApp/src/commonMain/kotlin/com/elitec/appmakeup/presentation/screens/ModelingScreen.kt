package com.elitec.appmakeup.presentation.screens

import androidx.compose.foundation.layout.Column
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
import com.elitec.appmakeup.domain.model.DomainEntity
import com.elitec.appmakeup.domain.model.EntityProperty
import com.elitec.appmakeup.domain.model.Feature
import com.elitec.appmakeup.domain.model.Project
import com.elitec.appmakeup.domain.model.ProjectLocation
import com.elitec.appmakeup.domain.model.PropertyType
import com.elitec.appmakeup.presentation.components.FeatureInspectorPanel
import com.elitec.appmakeup.presentation.components.FeatureListPanel
import com.elitec.appmakeup.presentation.components.ModelingTopBar
import com.elitec.appmakeup.presentation.util.ModelingMode
import com.elitec.appmakeup.presentation.viewmodels.ModelingViewModel
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun ModelingScreen(
    project: Project,
    mode: ModelingMode,
    onBack: () -> Unit,
    viewModel: ModelingViewModel = koinViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(project) {
        viewModel.initialize(project)
    }

    Column(Modifier.fillMaxSize()) {

        ModelingTopBar(
            projectName = project.name,
            isDirty = state.isDirty,
            onSave = {},
            onGenerate = { viewModel.generate() }
        )

        Divider()

        val selectedFeature =
            state.project?.features?.firstOrNull { it.name == state.selectedFeatureName }

        Row(Modifier.fillMaxSize()) {

            FeatureListPanel(
                features = state.project?.features.orEmpty(),
                selectedFeatureName = state.selectedFeatureName,
                onSelectFeature = viewModel::selectFeature,
                onAddFeature = {
                    viewModel.addFeature(
                        Feature(
                            name = "feature${state.project?.features?.size ?: 0}",
                            entity = DomainEntity("Entity", emptyList())
                        )
                    )
                }
            )

            Divider(Modifier.fillMaxHeight().width(1.dp))

            FeatureInspectorPanel(
                feature = selectedFeature,
                onAddProperty = {
                    viewModel.addProperty(
                        EntityProperty(
                            name = "property",
                            type = PropertyType.StringType
                        )
                    )
                }
            )
        }
    }
}