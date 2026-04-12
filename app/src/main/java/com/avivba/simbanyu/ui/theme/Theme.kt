package com.avivba.simbanyu.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider


@Composable
fun SimbanyuTheme(
    darkMode: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkMode) SimbanyuDarkColorScheme else SimbanyuColorScheme()

    CompositionLocalProvider(
        LocalSimbanyuColorScheme provides colorScheme,
        LocalSimbanyuTypography provides SimbanyuTypography(),
        LocalSimbanyuShapes provides SimbanyuShapes(),
        LocalSimbanyuSpacing provides SimbanyuSpacing(),
        LocalSimbanyuPadding provides SimbanyuPadding(),
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

    val padding: SimbanyuPadding
        @Composable
        get() = LocalSimbanyuPadding.current

    val shapes: SimbanyuShapes
        @Composable
        get() = LocalSimbanyuShapes.current

    val spacing: SimbanyuSpacing
        @Composable
        get() = LocalSimbanyuSpacing.current

    val typography: SimbanyuTypography
        @Composable
        get() = LocalSimbanyuTypography.current
}