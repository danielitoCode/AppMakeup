package com.elitec.appmakeup.projects.usecase.feature

import com.elitec.appmakeup.projects.model.AppFeature
import com.elitec.appmakeup.projects.model.AppMakeupProject

class GetFeatureUseCase {

    fun execute(
        project: AppMakeupProject,
        featureName: String
    ): AppFeature? =
        project.features.firstOrNull { it.name == featureName }
}