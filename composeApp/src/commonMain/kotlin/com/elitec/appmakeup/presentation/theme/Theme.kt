package com.elitec.appmakeup.presentation.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color

// Colores para el tema oscuro
val DarkColorScheme = darkColorScheme(
    primary = Color(0xFF5B88B2),          // Azul medio (base)
    onPrimary = Color(0xFFE6E6E6),
    primaryContainer = Color(0xFF3A5E7E), // Azul oscuro para contraste
    onPrimaryContainer = Color(0xFFD6E3F3),

    secondary = Color(0xFF4E6E8F),        // Azul grisáceo
    onSecondary = Color(0xFFE6E6E6),
    secondaryContainer = Color(0xFF39516F),
    onSecondaryContainer = Color(0xFFD8E3F0),

    tertiary = Color(0xFF8C4A6F),         // Magenta oscuro
    onTertiary = Color(0xFFE6E6E6),
    tertiaryContainer = Color(0xFF6A3857),
    onTertiaryContainer = Color(0xFFF3D5E6),

    background = Color(0xFF121212),       // Negro puro
    onBackground = Color(0xFFE6E6E6),

    surface = Color(0xFF1E252F),          // Gris azulado oscuro
    onSurface = Color(0xFFE6E6E6),
    surfaceVariant = Color(0xFF2A323E),
    onSurfaceVariant = Color(0xFFB0B0B0),

    error = Color(0xFFCF6679),            // Rojo suave
    onError = Color(0xFF121212),
    errorContainer = Color(0xFFB00020),
    onErrorContainer = Color(0xFFE6E6E6)
)

val LightColorScheme = lightColorScheme(
    primary = Color(0xFF5B88B2),          // Azul medio (color base)
    onPrimary = Color(0xFFFFFFFF),        // Blanco para contraste
    primaryContainer = Color(0xFFD5E3F3), // Azul muy claro (fondo de contenedores)
    onPrimaryContainer = Color(0xFF0F1D2B),

    secondary = Color(0xFF6B94B5),        // Azul-grisáceo secundario
    onSecondary = Color(0xFFFFFFFF),
    secondaryContainer = Color(0xFFD8E3F0),
    onSecondaryContainer = Color(0xFF172430),

    tertiary = Color(0xFFB25B8E),         // Magenta suave de apoyo
    onTertiary = Color(0xFFFFFFFF),
    tertiaryContainer = Color(0xFFF3D5E6),
    onTertiaryContainer = Color(0xFF2B0F1D),

    background = Color(0xFFFFFFFF),       // Blanco puro
    onBackground = Color(0xFF1C2526),

    surface = Color(0xFFFAFAFA),          // Gris muy claro
    onSurface = Color(0xFF1C2526),
    surfaceVariant = Color(0xFFE1E7ED),   // Gris-azulado suave
    onSurfaceVariant = Color(0xFF454F57),

    error = Color(0xFFB00020),            // Rojo brillante
    onError = Color(0xFFFFFFFF),
    errorContainer = Color(0xFFFCDADA),
    onErrorContainer = Color(0xFF410002)
)

@Immutable
data class ColorFamily(
    val color: Color,
    val onColor: Color,
    val colorContainer: Color,
    val onColorContainer: Color
)

val unspecified_scheme = ColorFamily(
    Color.Unspecified, Color.Unspecified, Color.Unspecified, Color.Unspecified
)

@Composable
fun AppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    content: @Composable() () -> Unit
) {
    val colorScheme = if (darkTheme) { DarkColorScheme } else { LightColorScheme }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = AppTypography,
        content = content
    )
}

