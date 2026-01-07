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
import org.jetbrains.compose.resources.painterResource
import org.koin.core.context.startKoin

fun main() = application {
    val windowState = WindowState(
        position = WindowPosition(alignment = Alignment.Center),
        placement = WindowPlacement.Floating
    )
    startKoin {
    }
    Window(
        resizable = false,
        undecorated = true,
        state = windowState,
        icon = painterResource(Res.drawable.toolicon),
        onCloseRequest = ::exitApplication,
        title = "AppMakeup",
    ) {

    }
}