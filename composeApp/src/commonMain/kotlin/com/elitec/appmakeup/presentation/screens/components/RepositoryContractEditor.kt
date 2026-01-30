package com.elitec.appmakeup.presentation.screens.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.elitec.appmakeup.core.v5.domain.contracts.EditableRepositoryContract

@Composable
fun RepositoryContractEditor(
    contract: EditableRepositoryContract,
    onChange: (EditableRepositoryContract) -> Unit
) {
    Column {
        Text("Repository for ${contract.entityName}")

        Row {
            Checkbox(
                checked = contract.supportsCreate,
                onCheckedChange = {
                    onChange(contract.copy(supportsCreate = !contract.supportsCreate))
                }
            )
            Text("Create")
        }

        Row {
            Checkbox(
                checked = contract.supportsRead,
                onCheckedChange = {
                    onChange(contract.copy(supportsRead = !contract.supportsRead))
                }
            )
            Text("Read")
        }

        Row {
            Checkbox(
                checked = contract.supportsUpdate,
                onCheckedChange = {
                    onChange(contract.copy(supportsRead = !contract.supportsUpdate))
                }
            )
            Text("Update")
        }

        Row {
            Checkbox(
                checked = contract.supportsDelete,
                onCheckedChange = {
                    onChange(contract.copy(supportsRead = !contract.supportsDelete))
                }
            )
            Text("Delete")
        }
    }
}