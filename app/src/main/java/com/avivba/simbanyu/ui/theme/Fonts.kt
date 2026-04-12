package com.avivba.simbanyu.ui.theme

import androidx.compose.ui.text.ExperimentalTextApi
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontVariation
import com.avivba.simbanyu.R

@OptIn(ExperimentalTextApi::class)
val FontInterRegular = FontFamily(
    Font(
        R.font.inter,
        variationSettings = FontVariation.Settings(
            FontVariation.weight(500)
        )
    )
)

@OptIn(ExperimentalTextApi::class)
val FontPlusJakartaSansRegular = FontFamily(
    Font(
        R.font.plus_jakarta_sans,
        variationSettings = FontVariation.Settings(
            FontVariation.weight(500)
        )
    )
)
