package com.elitec.appmakeup.domain.modeling.entity
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
sealed class PropertyType {
    // -------------------------
    // Primitive types
    // -------------------------

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
    @SerialName("float")
    data object FloatType : PropertyType()

    @Serializable
    @SerialName("double")
    data object DoubleType : PropertyType()

    // -------------------------
    // Special types
    // -------------------------

    @Serializable
    @SerialName("uuid")
    data object UUIDType : PropertyType()

    @Serializable
    @SerialName("datetime")
    data object DateTimeType : PropertyType()

    // -------------------------
    // Enum type
    // -------------------------

    @Serializable
    @SerialName("enum")
    data class EnumType(
        val name: String,
        val values: List<String>
    ) : PropertyType()

    // -------------------------
    // Value Object
    // -------------------------

    @Serializable
    @SerialName("value_object")
    data class ValueObjectType(
        val name: String
    ) : PropertyType()
}