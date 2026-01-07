package com.elitec.appmakeup.presentation.screens.expanded

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import appmakeup.composeapp.generated.resources.Res
import appmakeup.composeapp.generated.resources.sinfotoicon
import kotlinx.coroutines.delay
import org.jetbrains.compose.resources.painterResource

@Composable
fun SplashScreen(
    onDataReady: () -> Unit,
    modifier: Modifier = Modifier
) {
    LaunchedEffect(onDataReady) {
        delay(2000)
        onDataReady()
    }
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                painter = painterResource(Res.drawable.sinfotoicon),
                contentDescription = null,
                modifier = Modifier.size(300.dp),
                tint = MaterialTheme.colorScheme.onBackground
            )
            Spacer(Modifier.height(15.dp))
            Text(
                fontWeight = FontWeight.Bold,
                style = MaterialTheme.typography.headlineMedium,
                text = "APPMakeUP"
            )
        }
    }
}