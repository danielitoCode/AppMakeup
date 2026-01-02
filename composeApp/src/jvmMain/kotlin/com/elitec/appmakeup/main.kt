package com.elitec.appmakeup

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowPlacement
import androidx.compose.ui.window.WindowPosition
import androidx.compose.ui.window.WindowState
import androidx.compose.ui.window.application
import appmakeup.composeapp.generated.resources.Res
import appmakeup.composeapp.generated.resources.toolicon
import com.elitec.appmakeup.infraestructure.di.getCacheModules
import com.elitec.appmakeup.infraestructure.di.getDataModule
import com.elitec.appmakeup.infraestructure.di.getDomainModule
import com.elitec.appmakeup.infraestructure.di.getMigrationModule
import com.elitec.appmakeup.infraestructure.di.getTemplateModule
import com.elitec.appmakeup.infraestructure.di.getViewModelModule
import com.elitec.appmakeup.presentation.AppRoot
import org.jetbrains.compose.resources.painterResource
import org.koin.core.context.startKoin

fun main() = application {
    val windowState = WindowState(
        position = WindowPosition(alignment = Alignment.Center),
        placement = WindowPlacement.Floating
    )
    startKoin {
        modules(
            getDataModule(),
            getCacheModules(),
            getMigrationModule(),
            getTemplateModule(),
            getDomainModule(),
            getViewModelModule()
        )
    }
    Window(
        resizable = false,
        undecorated = true,
        state = windowState,
        icon = painterResource(Res.drawable.toolicon),
        onCloseRequest = ::exitApplication,
        title = "AppMakeup",
    ) {
        AppRoot(
            maximize = {
                windowState.isMinimized = false
                if(windowState.placement == WindowPlacement.Maximized) {
                    windowState.placement = WindowPlacement.Floating
                } else {
                    windowState.placement = WindowPlacement.Maximized
                }

            },
            minimize = {
                windowState.isMinimized = true
                windowState.placement = WindowPlacement.Floating
            },
            exit = ::exitApplication,
            modifier = Modifier.fillMaxSize()
        )
    }
}