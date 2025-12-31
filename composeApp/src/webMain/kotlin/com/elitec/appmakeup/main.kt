package com.elitec.appmakeup

import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.window.ComposeViewport
import com.elitec.appmakeup.infraestructure.di.getDataModule
import org.koin.core.context.startKoin

@OptIn(ExperimentalComposeUiApi::class)
fun main() {
    ComposeViewport {
        startKoin {
            getDataModule()
        }
        App()
    }
}