package com.elitec.appmakeup.core.v5.generators.mapper

import com.elitec.appmakeup.core.v4.contracts.RepositoryContract
import com.elitec.appmakeup.core.v4.definition.CoreEntity
import com.elitec.appmakeup.core.v5.domain.contracts.EditableRepositoryContract

class EditableToCoreRepositoryMapper {

    fun map(
        editable: EditableRepositoryContract,
        entity: CoreEntity
    ): RepositoryContract {

        require(editable.isValid()) {
            "Repository contract for '${editable.entityName}' is invalid"
        }

        return RepositoryContract(
            entity = entity,
            supportsCreate = editable.supportsCreate,
            supportsRead = editable.supportsRead,
            supportsUpdate = editable.supportsUpdate,
            supportsDelete = editable.supportsDelete
        )
    }
}