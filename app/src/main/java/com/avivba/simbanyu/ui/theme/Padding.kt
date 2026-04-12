package com.avivba.simbanyu.ui.theme

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Immutable
data class SimbanyuPadding(
    val default: Dp = 0.dp,
    val extraSmall: Dp = 2.dp,
    val small: Dp = 4.dp,
    val medium: Dp = 8.dp,
    val large: Dp = 16.dp,
    val extraLarge: Dp = 24.dp,
    val extraExtraLarge: Dp = 32.dp
)

val LocalSimbanyuPadding = staticCompositionLocalOf { SimbanyuPadding() }