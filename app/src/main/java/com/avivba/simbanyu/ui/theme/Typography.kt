package com.avivba.simbanyu.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle

@Immutable
data class SimbanyuTypography(
    val textField: TextStyle = TextStyle(
        color = Color.Red
    ),
    val label: TextStyle = TextStyle.Default,
)

val LocalSimbanyuTypography = staticCompositionLocalOf { SimbanyuTypography() }



