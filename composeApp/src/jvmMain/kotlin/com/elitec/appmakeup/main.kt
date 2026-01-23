package com.elitec.appmakeup

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Maximize
import androidx.compose.material.icons.filled.Minimize
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowPlacement
import androidx.compose.ui.window.WindowPosition
import androidx.compose.ui.window.WindowState
import androidx.compose.ui.window.application
import appmakeup.composeapp.generated.resources.Res
import appmakeup.composeapp.generated.resources.sinfotow
import appmakeup.composeapp.generated.resources.toolicon
import com.elitec.appmakeup.di.initKoin
import com.elitec.appmakeup.presentation.navigation.MainNavigationWrapper
import org.jetbrains.compose.resources.painterResource
import org.koin.core.context.startKoin

fun main() = application {
    val windowState = WindowState(
        position = WindowPosition(alignment = Alignment.Center),
        placement = WindowPlacement.Floating
    )
    LaunchedEffect(null) {
        initKoin()
    }

    Window(
        resizable = false,
        undecorated = true,
        state = windowState,
        icon = painterResource(Res.drawable.toolicon),
        onCloseRequest = ::exitApplication,
        title = "AppMakeup",
    ) {
        Scaffold(
            topBar = {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.End,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Surface(
                        shape = RoundedCornerShape(5.dp),
                        color = MaterialTheme.colorScheme.onBackground,
                        onClick = {
                            windowState.isMinimized = true
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Default.Minimize,
                            contentDescription = "",
                            tint = MaterialTheme.colorScheme.background,
                            modifier = Modifier.size(20.dp)
                        )
                    }

                    Spacer(modifier = Modifier.width(8.dp))
                    Surface(
                        shape = RoundedCornerShape(5.dp),
                        color = MaterialTheme.colorScheme.onBackground,
                        onClick = {
                            if(windowState.placement == WindowPlacement.Maximized) {
                                windowState.placement = WindowPlacement.Floating
                                return@Surface
                            }
                            windowState.placement = WindowPlacement.Maximized
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Default.Maximize,
                            contentDescription = "",
                            tint = MaterialTheme.colorScheme.background,
                            modifier = Modifier.size(20.dp)
                        )
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Surface(
                        shape = RoundedCornerShape(5.dp),
                        color = MaterialTheme.colorScheme.onBackground,
                        onClick = {
                            exitApplication()
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = "",
                            tint = MaterialTheme.colorScheme.background,
                            modifier = Modifier.size(20.dp)
                        )
                    }
                }
            },
            bottomBar = {}
        ) { innerPaddings ->
            MainNavigationWrapper(
                modifier = Modifier.fillMaxSize().padding(innerPaddings)
            )
        }
    }
}