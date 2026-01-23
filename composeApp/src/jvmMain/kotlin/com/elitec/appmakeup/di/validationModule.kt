package com.elitec.appmakeup.di

import com.elitec.appmakeup.core.v4.pipeline.ValidationStage
import com.elitec.appmakeup.core.v4.validation.ArchitectureValidator
import com.elitec.appmakeup.core.v4.validation.DefaultValidationStage
import com.elitec.appmakeup.core.v4.validation.EntityValidator
import com.elitec.appmakeup.core.v4.validation.FeatureValidator
import org.koin.dsl.module

val validationModule = module {

    single { EntityValidator() }
    single { FeatureValidator(get()) }
    single { ArchitectureValidator() }

    single<ValidationStage> {
        DefaultValidationStage(
            featureValidator = get(),
            architectureValidator = get()
        )
    }
}