package com.elitec.appmakeup.domain.modeling.entity

sealed class PropertyType {

    data object StringType : PropertyType()
    data object IntType : PropertyType()
    data object LongType : PropertyType()
    data object BooleanType : PropertyType()
    data object DateTimeType : PropertyType()

    data class CustomType(
        val name: String
    ) : PropertyType()
}