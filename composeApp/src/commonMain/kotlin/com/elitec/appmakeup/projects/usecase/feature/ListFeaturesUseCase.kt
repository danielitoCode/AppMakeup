package com.elitec.appmakeup.projects.usecase.feature

import com.elitec.appmakeup.projects.model.AppFeature
import com.elitec.appmakeup.projects.model.AppMakeupProject

class ListFeaturesUseCase {

    fun execute(project: AppMakeupProject): List<AppFeature> =
        project.features
}