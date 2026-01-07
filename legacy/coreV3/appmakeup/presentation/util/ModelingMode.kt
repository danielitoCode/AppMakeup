package com.elitec.appmakeup.presentation.util

enum class ModelingMode {
    CREATE,
    OPEN
}

fun String.toModelingMode(): ModelingMode = when(this) {
    "CREATE" -> ModelingMode.CREATE
    "OPEN" -> ModelingMode.OPEN
    else -> throw IllegalArgumentException("A agregado a la ruta un Modeling Mode incorrecto o no definido")
}