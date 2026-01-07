package com.elitec.appmakeup.presentation.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

val VisualStudioDarkColorScheme = darkColorScheme(

    primary = Color(0xFF007ACC),
    onPrimary = Color.White,

    secondary = Color(0xFF3794FF),
    onSecondary = Color.Black,

    background = Color(0xFF1E1E1E),
    onBackground = Color(0xFFD4D4D4),

    surface = Color(0xFF252526),
    onSurface = Color(0xFFD4D4D4),

    surfaceVariant = Color(0xFF2D2D30),
    onSurfaceVariant = Color(0xFFCCCCCC),

    outline = Color(0xFF3C3C3C),

    error = Color(0xFFD16969),
    onError = Color.Black
)

val VisualStudioLightColorScheme = lightColorScheme(

    primary = Color(0xFF007ACC),
    onPrimary = Color.White,

    secondary = Color(0xFF0063B1),
    onSecondary = Color.White,

    background = Color(0xFFFFFFFF),
    onBackground = Color(0xFF1E1E1E),

    surface = Color(0xFFF3F3F3),
    onSurface = Color(0xFF1E1E1E),

    surfaceVariant = Color(0xFFEDEDED),
    onSurfaceVariant = Color(0xFF444444),

    outline = Color(0xFFE1E1E1),

    error = Color(0xFFC42B1C),
    onError = Color.White
)

@Composable
fun AppMakeupTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme =
        if (darkTheme) VisualStudioDarkColorScheme
        else VisualStudioLightColorScheme

    MaterialTheme(
        colorScheme = colorScheme,
        typography = MaterialTheme.typography,
        content = content
    )
}