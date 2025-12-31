package com.elitec.appmakeup

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview

import appmakeup.composeapp.generated.resources.Res
import appmakeup.composeapp.generated.resources.compose_multiplatform
import com.elitec.appmakeup.presentation.screens.expanded.ModelingScreen
import com.elitec.appmakeup.presentation.screens.expanded.WelcomeScreen
import com.elitec.appmakeup.presentation.theme.AppMakeupTheme
import com.elitec.appmakeup.presentation.uiStates.AppState

@Composable
@Preview
fun App() {
    /*var appState by remember { mutableStateOf<AppState>(AppState.Welcome) }
    AppMakeupTheme(darkTheme = isSystemInDarkTheme()) {
        when (val state = appState) {
            AppState.Welcome -> {
                WelcomeScreen(
                    onCreateProject = { project, location ->
                        appState = AppState.Modeling(project, location)
                    }
                )
            }

            is AppState.Modeling -> {
                ModelingScreen(
                    initialProject = state.project,
                    initialLocation = state.location
                )
            }
        }
    }*/
    /*MaterialTheme {
        var showContent by remember { mutableStateOf(false) }
        Column(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.primaryContainer)
                .safeContentPadding()
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Button(onClick = { showContent = !showContent }) {
                Text("Click me!")
            }
            AnimatedVisibility(showContent) {
                val greeting = remember { Greeting().greet() }
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    Image(painterResource(Res.drawable.compose_multiplatform), null)
                    Text("Compose: $greeting")
                }
            }
        }
    }*/
}