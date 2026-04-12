package com.avivba.simbanyu.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

@Immutable
data class SimbanyuTypography(
    private val base: TextStyle = TextStyle(
        fontFamily = FontInterRegular
    ),
    val body: TextStyle = base.copy(
        fontSize = 14.sp
    ),
    val textField: TextStyle = base.copy(),
    val button: TextStyle = base.copy(
        fontFamily = FontPlusJakartaSansRegular,
        fontSize = 16.sp,
        fontWeight = FontWeight.Bold
    ),
    val label: TextStyle = base.copy(
        fontWeight = FontWeight.ExtraBold,
        fontSize = 10.sp,
        letterSpacing = 2.sp
    ),
    val title: TextStyle = base.copy(
        fontFamily = FontPlusJakartaSansRegular,
        fontWeight = FontWeight.Bold,
        fontSize = 24.sp
    ),
    val titleLarge: TextStyle = title.copy(
        fontSize = 32.sp
    )
)

val LocalSimbanyuTypography = staticCompositionLocalOf { SimbanyuTypography() }