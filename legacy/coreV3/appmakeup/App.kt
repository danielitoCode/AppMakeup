package com.elitec.appmakeup

import androidx.compose.runtime.*
import org.jetbrains.compose.ui.tooling.preview.Preview

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