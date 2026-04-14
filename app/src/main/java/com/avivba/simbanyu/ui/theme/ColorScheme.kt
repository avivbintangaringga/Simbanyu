package com.avivba.simbanyu.ui.theme

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color

@Immutable
data class SimbanyuColorScheme(
    val primary: Color = Blue30,
    val primaryLight: Color = Blue40,
    val primaryGradient: List<Color> = listOf(
        primary,
        primaryLight
    ),
    val primaryGradientVerticalBrush: Brush = Brush.verticalGradient(primaryGradient),
    val primaryGradientHorizontalBrush: Brush = Brush.horizontalGradient(primaryGradient),
    val onPrimary: Color = Color.White,
    val primaryContainer: Color = Grey95,

    val background: Color = Blue95,
    val error: Color = Color.Red,

    val cardBackground: Color = Color.White,
    val text: Color = Grey20,
    val textField: Color = Grey20,
    val textFieldHint: Color = textField.copy(alpha = 0.5f),

    val shadow: Color = Grey30
)

val SimbanyuDarkColorScheme = SimbanyuColorScheme(
    primaryGradientVerticalBrush = Brush.verticalGradient()
)

val LocalSimbanyuColorScheme = staticCompositionLocalOf { SimbanyuColorScheme() }
