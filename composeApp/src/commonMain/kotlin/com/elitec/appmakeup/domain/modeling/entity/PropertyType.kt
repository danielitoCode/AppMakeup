package com.elitec.appmakeup.domain.modeling.entity
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
sealed class PropertyType {
    @Serializable
    @SerialName("string")
    data object StringType : PropertyType()

    @Serializable
    @SerialName("int")
    data object IntType : PropertyType()

    @Serializable
    @SerialName("long")
    data object LongType : PropertyType()

    @Serializable
    @SerialName("boolean")
    data object BooleanType : PropertyType()

    @Serializable
    @SerialName("datetime")
    data object DateTimeType : PropertyType()

    @Serializable
    @SerialName("custom")
    data class CustomType(
        val name: String
    ) : PropertyType()
}