package com.avivba.simbanyu.ui.theme

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.unit.dp

@Immutable
data class SimbanyuShapes(
    val extraSmall: RoundedCornerShape = RoundedCornerShape(2.dp),
    val small: RoundedCornerShape = RoundedCornerShape(4.dp),
    val medium: RoundedCornerShape = RoundedCornerShape(8.dp),
    val large: RoundedCornerShape = RoundedCornerShape(16.dp),
    val extraLarge: RoundedCornerShape = RoundedCornerShape(24.dp),
    val extraExtraLarge: RoundedCornerShape = RoundedCornerShape(32.dp)
)

val LocalSimbanyuShapes = staticCompositionLocalOf { SimbanyuShapes() }