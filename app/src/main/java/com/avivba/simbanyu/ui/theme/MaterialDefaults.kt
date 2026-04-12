package com.avivba.simbanyu.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.material3.lightColorScheme
import androidx.compose.ui.graphics.Color

val MaterialColorScheme = lightColorScheme(
    primary = Blue50,
    onPrimary = Color.White,
    primaryContainer = Blue90,
    onPrimaryContainer = Blue10,
    secondary = Sky50,
    onSecondary = Color.White,
    secondaryContainer = Sky90,
    onSecondaryContainer = Sky10,
    tertiary = Brown50,
    onTertiary = Color.White,
    background = Color(0xFFF8F9FA),
    onBackground = Grey10,
    surface = Color.White,
    onSurface = Grey10,
    surfaceVariant = Color(0xFFE9EEF5),
    onSurfaceVariant = Grey30,
    outline = Grey80
)

private val materialDefaultTypography = Typography()

// Set of Material typography styles to start with
val MaterialTypography = Typography(
    displayLarge = materialDefaultTypography.displayLarge.copy(fontFamily = FontPlusJakartaSansRegular),
    displayMedium = materialDefaultTypography.displayMedium.copy(fontFamily = FontPlusJakartaSansRegular),
    displaySmall = materialDefaultTypography.displaySmall.copy(fontFamily = FontPlusJakartaSansRegular),
    headlineLarge = materialDefaultTypography.headlineLarge.copy(fontFamily = FontPlusJakartaSansRegular),
    headlineMedium = materialDefaultTypography.headlineMedium.copy(fontFamily = FontPlusJakartaSansRegular),
    headlineSmall = materialDefaultTypography.headlineSmall.copy(fontFamily = FontPlusJakartaSansRegular),
    titleLarge = materialDefaultTypography.titleLarge.copy(fontFamily = FontPlusJakartaSansRegular),
    titleMedium = materialDefaultTypography.titleMedium.copy(fontFamily = FontPlusJakartaSansRegular),
    titleSmall = materialDefaultTypography.titleSmall.copy(fontFamily = FontPlusJakartaSansRegular),
    bodyLarge = materialDefaultTypography.bodyLarge.copy(fontFamily = FontInterRegular),
    bodyMedium = materialDefaultTypography.bodyMedium.copy(fontFamily = FontInterRegular),
    bodySmall = materialDefaultTypography.bodySmall.copy(fontFamily = FontInterRegular),
    labelLarge = materialDefaultTypography.labelLarge.copy(fontFamily = FontInterRegular),
    labelMedium = materialDefaultTypography.labelMedium.copy(fontFamily = FontInterRegular),
    labelSmall = materialDefaultTypography.labelSmall.copy(fontFamily = FontInterRegular)
)
