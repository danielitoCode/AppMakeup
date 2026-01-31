package com.elitec.appmakeup.presentation.screens.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import com.elitec.appmakeup.core.v5.domain.contracts.EditableRepositoryContract
import com.elitec.appmakeup.projects.model.AppEntity

@Composable
fun RepositoryContractPanel(
    entity: AppEntity,
    contract: EditableRepositoryContract?,
    onUpdate: (EditableRepositoryContract) -> Unit
) {
    val current = contract ?: EditableRepositoryContract(entity.name)

    Column {
        Text("Repository for ${entity.name}")

        listOf(
            "Create" to current.supportsCreate,
            "Read" to current.supportsRead,
            "Update" to current.supportsUpdate,
            "Delete" to current.supportsDelete
        ).forEach { (label, checked) ->
            Row(verticalAlignment = Alignment.CenterVertically) {
                Checkbox(
                    checked = checked,
                    onCheckedChange = {
                        onUpdate(
                            current.copy(
                                supportsCreate = if (label == "Create") it else current.supportsCreate,
                                supportsRead = if (label == "Read") it else current.supportsRead,
                                supportsUpdate = if (label == "Update") it else current.supportsUpdate,
                                supportsDelete = if (label == "Delete") it else current.supportsDelete
                            )
                        )
                    }
                )
                Text(label)
            }
        }
    }
}