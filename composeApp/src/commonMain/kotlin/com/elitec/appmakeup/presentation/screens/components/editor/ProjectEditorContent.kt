package com.elitec.appmakeup.presentation.screens.components.editor

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.elitec.appmakeup.core.v5.domain.contracts.EditableRepositoryContract
import com.elitec.appmakeup.presentation.screens.components.EntityEditorPanel
import com.elitec.appmakeup.presentation.screens.components.EntityListPanel
import com.elitec.appmakeup.presentation.screens.components.RelationEditorPanel
import com.elitec.appmakeup.presentation.screens.components.RepositoryContractPanel
import com.elitec.appmakeup.presentation.screens.components.ValidationPanel
import com.elitec.appmakeup.presentation.states.ProjectEditorUiState
import com.elitec.appmakeup.projects.model.AppProperty
import com.elitec.appmakeup.projects.model.AppRelation

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
    onAddRelation: (String, AppRelation) -> Unit,
    onDeleteRelation: (String, AppRelation) -> Unit,
    onExport: () -> Unit
) {
    Row(Modifier.fillMaxSize()) {

        FeatureListPanel(
            features = state.features,
            selectedFeature = state.selectedFeature,
            onSelectFeature = onSelectFeature,
            onAddFeature = onAddFeature,
            modifier = Modifier.weight(0.25f)
        )

        VerticalDivider()

        EntityListPanel(
            feature = state.selectedFeature,
            selectedEntity = state.selectedEntity,
            onSelectEntity = onSelectEntity,
            onAddEntity = onAddEntity,
            modifier = Modifier.weight(0.25f)
        )

        VerticalDivider()

        // 👉 PANEL DERECHO CON SCROLL
        Box(
            Modifier
                .weight(0.5f)
                .padding(16.dp)
                .verticalScroll(rememberScrollState())
        ) {
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

                    RelationEditorPanel(
                        feature = feature,
                        onAddRelation = { relation ->
                            onAddRelation(feature.name, relation)
                        },
                        onDeleteRelation = { rel ->
                            onDeleteRelation(feature.name, rel)
                        }
                    )
                }
            }
        }
    }

    if (state.validationErrors.isNotEmpty()) {
        ValidationPanel(state.validationIssues)
    }

    Box(Modifier.fillMaxSize()) {
        Button(
            onClick = onExport,
            enabled = state.canExport,
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(24.dp)
        ) {
            Icon(Icons.Default.PlayArrow, contentDescription = "Export")
        }
    }
}