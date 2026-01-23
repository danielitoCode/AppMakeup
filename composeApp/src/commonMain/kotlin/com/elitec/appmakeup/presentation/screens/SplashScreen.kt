package com.elitec.appmakeup.presentation.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import appmakeup.composeapp.generated.resources.Res
import appmakeup.composeapp.generated.resources.sinfoto
import com.elitec.appmakeup.presentation.navigation.MainScreens
import kotlinx.coroutines.delay
import org.jetbrains.compose.resources.painterResource

@Composable
fun SplashScreen(
    navigateTo: (MainScreens) -> Unit,
    modifier: Modifier = Modifier
) {
    LaunchedEffect(null) {
        delay(1000)
        navigateTo(MainScreens.Home)
    }
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier.fillMaxSize()
    ) {
        Image(
            painter = painterResource(Res.drawable.sinfoto),
            contentDescription = "",
            modifier = Modifier.size(200.dp)
        )
    }
}