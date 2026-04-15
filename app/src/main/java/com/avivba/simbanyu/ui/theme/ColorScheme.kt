package com.avivba.simbanyu.ui.theme

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color

data class SimbanyuBrush(
    val brandVertical: Brush,
    val brandHorizontal: Brush
)

@Immutable
data class SimbanyuColorScheme(
    val brand: Color = Blue30,
    val brandLight: Color = Blue40,
    val brandGradient: List<Color> = listOf(
        brand,
        brandLight
    ),
    val brandText: Color = Color.White,

    val actionBrand: Color = brand,
    val actionBrandText: Color = brandText,

    val screenBackground: Color = Color.White,

    val surfaceCard: Color = Color.White,
    val surfaceCardAlternate: Color = Grey95,
    val surfaceInput: Color = Grey90,

    val borderCard: Color = Grey95,
    val borderCardAlternate: Color = Grey90,

    val textHeading: Color = Grey20,
    val textLabel: Color = Grey20,
    val textBody: Color = Grey20,
    val textInput: Color = Grey20,
    val textInputDisabled: Color = textInput.copy(alpha = 0.5f),

    val shadow: Color = Grey30,

    // Brushes
    val brush: SimbanyuBrush = SimbanyuBrush(
        brandVertical = Brush.verticalGradient(brandGradient),
        brandHorizontal = Brush.horizontalGradient(brandGradient)
    ),

    // Status colors
    val statusPositive: Color = Color.Green,
    val statusPositiveText: Color = Color.White,
    val statusNegative: Color = Color.Red,
    val statusNegativeText: Color = Color.White,
    val statusWarning: Color = Color.Yellow,
    val statusWarningText: Color = Color.Black,
    val statusNeutral: Color = Color.Gray,
    val statusNeutralText: Color = Color.White,
)

val SimbanyuDarkColorScheme = SimbanyuColorScheme()

val LocalSimbanyuColorScheme = staticCompositionLocalOf { SimbanyuColorScheme() }
