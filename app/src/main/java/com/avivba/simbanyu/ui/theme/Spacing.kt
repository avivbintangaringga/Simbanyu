package com.avivba.simbanyu.ui.theme

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Immutable
data class SimbanyuSpacing(
    val small: Dp = 0.dp,
    val medium: Dp = 0.dp,
    val large: Dp = 0.dp,
)

val LocalSimbanyuSpacing = staticCompositionLocalOf { SimbanyuSpacing() }
