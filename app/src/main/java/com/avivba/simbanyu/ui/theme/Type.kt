package com.avivba.simbanyu.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.ExperimentalTextApi
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontVariation
import com.avivba.simbanyu.R

@OptIn(ExperimentalTextApi::class)
val interRegular = FontFamily(
    Font(
        R.font.inter,
        variationSettings = FontVariation.Settings(
            FontVariation.weight(500)
        )
    )
)

@OptIn(ExperimentalTextApi::class)
val plusJakartaSansRegular = FontFamily(
    Font(
        R.font.plus_jakarta_sans,
        variationSettings = FontVariation.Settings(
            FontVariation.weight(500)
        )
    )
)

private val defaultTypography = Typography()

// Set of Material typography styles to start with
val Typography = Typography(
    displayLarge = defaultTypography.displayLarge.copy(fontFamily = plusJakartaSansRegular),
    displayMedium = defaultTypography.displayMedium.copy(fontFamily = plusJakartaSansRegular),
    displaySmall = defaultTypography.displaySmall.copy(fontFamily = plusJakartaSansRegular),
    headlineLarge = defaultTypography.headlineLarge.copy(fontFamily = plusJakartaSansRegular),
    headlineMedium = defaultTypography.headlineMedium.copy(fontFamily = plusJakartaSansRegular),
    headlineSmall = defaultTypography.headlineSmall.copy(fontFamily = plusJakartaSansRegular),
    titleLarge = defaultTypography.titleLarge.copy(fontFamily = plusJakartaSansRegular),
    titleMedium = defaultTypography.titleMedium.copy(fontFamily = plusJakartaSansRegular),
    titleSmall = defaultTypography.titleSmall.copy(fontFamily = plusJakartaSansRegular),
    bodyLarge = defaultTypography.bodyLarge.copy(fontFamily = plusJakartaSansRegular),
    bodyMedium = defaultTypography.bodyMedium.copy(fontFamily = plusJakartaSansRegular),
    bodySmall = defaultTypography.bodySmall.copy(fontFamily = plusJakartaSansRegular),
    labelLarge = defaultTypography.labelLarge.copy(fontFamily = plusJakartaSansRegular),
    labelMedium = defaultTypography.labelMedium.copy(fontFamily = plusJakartaSansRegular),
    labelSmall = defaultTypography.labelSmall.copy(fontFamily = plusJakartaSansRegular)
)
