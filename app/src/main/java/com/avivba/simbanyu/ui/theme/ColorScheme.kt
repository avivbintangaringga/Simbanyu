package com.avivba.simbanyu.ui.theme

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Brush

@Immutable
data class SimbanyuColorScheme(
    val buttonPrimary: Brush = Brush.verticalGradient()
)

val SimbanyuDarkColorScheme = SimbanyuColorScheme()

val LocalSimbanyuColorScheme = staticCompositionLocalOf { SimbanyuColorScheme() }
