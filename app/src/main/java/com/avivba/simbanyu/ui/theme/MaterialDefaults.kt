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
    displayLarge = materialDefaultTypography.displayLarge.copy(fontFamily = plusJakartaSansRegular),
    displayMedium = materialDefaultTypography.displayMedium.copy(fontFamily = plusJakartaSansRegular),
    displaySmall = materialDefaultTypography.displaySmall.copy(fontFamily = plusJakartaSansRegular),
    headlineLarge = materialDefaultTypography.headlineLarge.copy(fontFamily = plusJakartaSansRegular),
    headlineMedium = materialDefaultTypography.headlineMedium.copy(fontFamily = plusJakartaSansRegular),
    headlineSmall = materialDefaultTypography.headlineSmall.copy(fontFamily = plusJakartaSansRegular),
    titleLarge = materialDefaultTypography.titleLarge.copy(fontFamily = plusJakartaSansRegular),
    titleMedium = materialDefaultTypography.titleMedium.copy(fontFamily = plusJakartaSansRegular),
    titleSmall = materialDefaultTypography.titleSmall.copy(fontFamily = plusJakartaSansRegular),
    bodyLarge = materialDefaultTypography.bodyLarge.copy(fontFamily = interRegular),
    bodyMedium = materialDefaultTypography.bodyMedium.copy(fontFamily = interRegular),
    bodySmall = materialDefaultTypography.bodySmall.copy(fontFamily = interRegular),
    labelLarge = materialDefaultTypography.labelLarge.copy(fontFamily = interRegular),
    labelMedium = materialDefaultTypography.labelMedium.copy(fontFamily = interRegular),
    labelSmall = materialDefaultTypography.labelSmall.copy(fontFamily = interRegular)
)
