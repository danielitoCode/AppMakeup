package com.elitec.appmakeup.core.v4.contracts

interface Template {
    val name: String

    fun render(
        model: Map<String, Any>
    ): String
}