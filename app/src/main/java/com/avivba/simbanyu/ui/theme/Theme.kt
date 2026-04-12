package com.avivba.simbanyu.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp


@Composable
fun SimbanyuTheme(
    darkMode: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkMode) SimbanyuDarkColorScheme else SimbanyuColorScheme()

    CompositionLocalProvider(
        LocalSimbanyuColorScheme provides colorScheme,
        LocalSimbanyuTypography provides SimbanyuTypography(),
        LocalSimbanyuSpacing provides SimbanyuSpacing(),
    ) {
        MaterialTheme(
            colorScheme = MaterialColorScheme,
            typography = MaterialTypography,
            content = content
        )
    }
}

object SimbanyuTheme {
    val colors: SimbanyuColorScheme
        @Composable
        get() = LocalSimbanyuColorScheme.current

    val spacing: SimbanyuSpacing
        @Composable
        get() = LocalSimbanyuSpacing.current

    val typography: SimbanyuTypography
        @Composable
        get() = LocalSimbanyuTypography.current
}