package com.elitec.appmakeup

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import com.elitec.appmakeup.infraestructure.di.getDataModule
import com.elitec.appmakeup.infraestructure.di.getDomainModule
import com.elitec.appmakeup.infraestructure.di.getViewModelModule
import com.elitec.appmakeup.presentation.AppRoot
import com.elitec.appmakeup.presentation.screens.expanded.ModelingScreen
import org.koin.core.context.startKoin

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "AppMakeup",
    ) {
        startKoin {
            modules(
                getDataModule(),
                getDomainModule(),
                getViewModelModule()
            )
        }
        AppRoot(modifier = Modifier.fillMaxSize())
    }
}