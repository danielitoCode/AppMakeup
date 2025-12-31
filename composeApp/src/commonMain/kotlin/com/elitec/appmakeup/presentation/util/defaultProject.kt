package com.elitec.appmakeup.presentation.util

import com.elitec.appmakeup.domain.architecture.ArchitectureConfig
import com.elitec.appmakeup.domain.architecture.Layer
import com.elitec.appmakeup.domain.project.Project

fun defaultProject(): Project =
    Project(
        name = "MyFirstApp",
        packageName = "com.example.myfirstapp",
        architecture = ArchitectureConfig(
            layers = listOf(
                Layer.DOMAIN,
                Layer.DATA,
                Layer.PRESENTATION,
                Layer.INFRASTRUCTURE
            )
        ),
        features = emptyList()
    )