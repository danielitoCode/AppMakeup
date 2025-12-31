package com.elitec.appmakeup

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import com.elitec.appmakeup.infraestructure.di.getDataModule
import org.koin.core.context.startKoin

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "AppMakeup",
    ) {
        startKoin {
            getDataModule()
        }
        App()
    }
}