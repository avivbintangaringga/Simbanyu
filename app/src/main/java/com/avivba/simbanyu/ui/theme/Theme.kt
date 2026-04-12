package com.avivba.simbanyu.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

private val DarkColorScheme = darkColorScheme(
    primary = Blue80,
    onPrimary = Blue20,
    primaryContainer = Blue30,
    onPrimaryContainer = Blue90,
    secondary = Sky80,
    onSecondary = Sky20,
    secondaryContainer = Sky30,
    onSecondaryContainer = Sky90,
    tertiary = Brown80,
    onTertiary = Brown20,
    background = Grey10,
    onBackground = Grey90,
    surface = Grey10,
    onSurface = Grey90,
    surfaceVariant = Grey30,
    onSurfaceVariant = Grey80,
    outline = Grey50
)

private val LightColorScheme = lightColorScheme(
    primary = Blue50,
    onPrimary = Color.White,
    primaryContainer = Blue90,
    onPrimaryContainer = Blue10,
    secondary = Sky50,
    onSecondary = Color.White,
    secondaryContainer = Sky90,
    onSecondaryContainer = Sky10,
    tertiary = Brown50,
    onTertiary = Color.White,
    background = Color(0xFFF8F9FA),
    onBackground = Grey10,
    surface = Color.White,
    onSurface = Grey10,
    surfaceVariant = Color(0xFFE9EEF5),
    onSurfaceVariant = Grey30,
    outline = Grey80
)

@Composable
fun SimbanyuTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
