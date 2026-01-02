package com.elitec.appmakeup.domain.modeling.entity

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
sealed interface RelationshipType {

    @Serializable
    @SerialName("one_to_one")
    data object OneToOne : RelationshipType

    @Serializable
    @SerialName("one_to_many")
    data object OneToMany : RelationshipType

    @Serializable
    @SerialName("many_to_one")
    data object ManyToOne : RelationshipType
}