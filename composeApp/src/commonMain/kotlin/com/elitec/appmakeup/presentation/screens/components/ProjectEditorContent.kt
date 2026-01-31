package com.elitec.appmakeup.presentation.screens.components

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Divider
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.FloatingActionButtonElevation
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.VerticalDivider
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.Modifier.Companion
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.dp
import com.elitec.appmakeup.core.v5.domain.contracts.EditableRepositoryContract
import com.elitec.appmakeup.presentation.states.ProjectEditorUiState
import com.elitec.appmakeup.projects.model.AppProperty

@Composable
fun ProjectEditorContent(
    state: ProjectEditorUiState,
    onSelectFeature: (String) -> Unit,
    onAddFeature: (String) -> Unit,
    onAddEntity: (String, String) -> Unit,
    onSelectEntity: (String) -> Unit,
    onAddProperty: (String, String, AppProperty) -> Unit,
    onRemoveProperty: (String, String, String) -> Unit,
    onUpdateRepositoryContract: (String, EditableRepositoryContract) -> Unit,
    onExport: () -> Unit
) {
    Row(Modifier.fillMaxSize()) {

        // 🔹 Panel 1: Features
        FeatureListPanel(
            features = state.features,
            selectedFeature = state.selectedFeature,
            onSelectFeature = onSelectFeature,
            onAddFeature = onAddFeature,
            modifier = Modifier.weight(0.25f)
        )

        VerticalDivider()

        // 🔹 Panel 2: Entities
        EntityListPanel(
            feature = state.selectedFeature,
            selectedEntity = state.selectedEntity,
            onSelectEntity = onSelectEntity,
            onAddEntity = onAddEntity,
            modifier = Modifier.weight(0.25f)
        )

        VerticalDivider()

        // 🔹 Panel 3: Entity editor + repository
        Box(Modifier.weight(0.5f).padding(16.dp)) {

            val feature = state.selectedFeature
            val entity = state.selectedEntity

            if (feature == null || entity == null) {
                Text(
                    "Select an entity to edit",
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            } else {
                Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {

                    EntityEditorPanel(
                        feature = feature,
                        entity = entity,
                        onAddProperty = onAddProperty,
                        onRemoveProperty = onRemoveProperty
                    )

                    Divider()

                    RepositoryContractPanel(
                        entity = entity,
                        contract = feature.repositoryContracts
                            .firstOrNull { it.entityName == entity.name },
                        onUpdate = {
                            onUpdateRepositoryContract(feature.name, it)
                        }
                    )

                    Divider()

                    // 🔹 NUEVO – HITO 5
                    RelationEditorPanel(
                        feature = feature,
                        onAddRelation = { from, to, type ->
                            // esto lo conectas al ViewModel
                            // viewModel.addRelation(...)
                        }
                    )
                }
            }
        }
    }

    if (state.validationErrors.isNotEmpty()) {
        ValidationPanel(state.validationErrors)
    }

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Button(
            onClick = onExport,
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(24.dp),
            enabled = state.canExport
        ) {
            Icon(Icons.Default.PlayArrow, contentDescription = "Export")
        }
    }
}