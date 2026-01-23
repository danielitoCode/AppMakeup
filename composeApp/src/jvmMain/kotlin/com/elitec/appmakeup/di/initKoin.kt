package com.elitec.appmakeup.di

import org.koin.core.context.GlobalContext.startKoin

fun initKoin() {
    startKoin {
        modules(
            infrastructureModule,
            applicationModule,
            presentationModule
        )
    }
}