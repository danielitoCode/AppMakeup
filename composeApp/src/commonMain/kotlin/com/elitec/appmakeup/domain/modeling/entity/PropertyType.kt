package com.elitec.appmakeup.domain.modeling.entity
import kotlinx.serialization.Serializable

@Serializable
sealed class PropertyType {
    @Serializable
    data object StringType : PropertyType()
    @Serializable
    data object IntType : PropertyType()
    @Serializable
    data object LongType : PropertyType()
    @Serializable
    data object BooleanType : PropertyType()
    @Serializable
    data object DateTimeType : PropertyType()
    @Serializable
    data class CustomType(
        val name: String
    ) : PropertyType()
}