package com.avivba.simbanyu.ui.theme

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

@Immutable
data class SimbanyuTypography(
    // --- DISPLAY (Large Numbers/Hero Stats) ---
    val displayHero: TextStyle = TextStyle(
        fontFamily = FontPlusJakartaSansRegular,
        fontSize = 38.sp,
        fontWeight = FontWeight.ExtraBold,
        lineHeight = 34.sp,
        letterSpacing = 1.sp
    ),
    val displayLarge: TextStyle = TextStyle(
        fontFamily = FontPlusJakartaSansRegular,
        fontSize = 32.sp,
        fontWeight = FontWeight.Bold,
        lineHeight = 28.sp
    ),
    val displayMedium: TextStyle = TextStyle(
        fontFamily = FontPlusJakartaSansRegular,
        fontSize = 22.sp,
        fontWeight = FontWeight.Bold,
        lineHeight = 28.sp
    ),
    val displaySmall: TextStyle = TextStyle(
        fontFamily = FontPlusJakartaSansRegular,
        fontSize = 18.sp,
        fontWeight = FontWeight.Bold,
        lineHeight = 24.sp,
    ),

    // --- TITLE (Hierarchy & Structure) ---
    val titlePage: TextStyle = TextStyle(
        fontFamily = FontPlusJakartaSansRegular,
        fontSize = 20.sp,
        fontWeight = FontWeight.Bold,
        lineHeight = 26.sp
    ),
    val titleSection: TextStyle = TextStyle(
        fontFamily = FontPlusJakartaSansRegular,
        fontSize = 16.sp,
        fontWeight = FontWeight.ExtraBold,
        lineHeight = 20.sp
    ),
    val titleCard: TextStyle = TextStyle(
        fontFamily = FontPlusJakartaSansRegular,
        fontSize = 16.sp,
        fontWeight = FontWeight.Bold,
        lineHeight = 20.sp
    ),

    // --- BODY (Content & Details) ---
    val bodyProminent: TextStyle = TextStyle(
        fontFamily = FontInterRegular,
        fontSize = 14.sp,
        fontWeight = FontWeight.SemiBold,
        lineHeight = 20.sp
    ),
    val bodyStandard: TextStyle = TextStyle(
        fontFamily = FontInterRegular,
        fontSize = 14.sp,
        fontWeight = FontWeight.Normal,
        lineHeight = 20.sp
    ),
    val bodySmall: TextStyle = TextStyle(
        fontFamily = FontInterRegular,
        fontSize = 12.sp,
        fontWeight = FontWeight.Normal,
        lineHeight = 16.sp
    ),

    // --- LABEL (Overlines & UI Controls) ---
    val labelOverline: TextStyle = TextStyle(
        fontFamily = FontInterRegular,
        fontSize = 12.sp,
        fontWeight = FontWeight.ExtraBold,
        lineHeight = 14.sp,
        letterSpacing = 1.15.sp
    ),
    val labelBadge: TextStyle = TextStyle(
        fontFamily = FontInterRegular,
        fontSize = 10.sp,
        fontWeight = FontWeight.ExtraBold,
        lineHeight = 12.sp,
        letterSpacing = 1.2.sp
    ),
    val labelAction: TextStyle = TextStyle(
        fontFamily = FontInterRegular,
        fontSize = 16.sp,
        fontWeight = FontWeight.Bold,
        lineHeight = 20.sp
    ),
    val labelUnit: TextStyle = TextStyle(
        fontFamily = FontInterRegular,
        fontSize = 12.sp,
        fontWeight = FontWeight.Bold,
        lineHeight = 16.sp
    )
)

val LocalSimbanyuTypography = staticCompositionLocalOf { SimbanyuTypography() }
