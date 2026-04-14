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
    // --- DISPLAY (Large Numbers/Hero Stats) ---
    // Used for "184.500" or "Rp 1.420.000"
    val displayHero: TextStyle = TextStyle(
        fontSize = 38.sp,
        fontWeight = FontWeight.ExtraBold,
        lineHeight = 34.sp,
        letterSpacing = 1.sp
    ),
    val displayLarge: TextStyle = TextStyle(
        fontSize = 32.sp,
        fontWeight = FontWeight.Bold,
        lineHeight = 28.sp
    ),
    // Used for "15 Pelanggan" in Dashboard Task Card
    val displayMedium: TextStyle = TextStyle(
        fontSize = 22.sp,
        fontWeight = FontWeight.Bold,
        lineHeight = 28.sp
    ),
    // Used for smaller prominent numbers like "24 m³"
    val displaySmall: TextStyle = TextStyle(
        fontSize = 18.sp,
        fontWeight = FontWeight.Bold,
        lineHeight = 24.sp
    ),

    // --- TITLE (Hierarchy & Structure) ---
    // Used for "Riwayat Tagihan", "Catat Meter"
    val titlePage: TextStyle = TextStyle(
        fontSize = 20.sp,
        fontWeight = FontWeight.Bold,
        lineHeight = 26.sp
    ),
    // Used for "RINCIAN BIAYA", "DATA KEUANGAN"
    val titleSection: TextStyle = TextStyle(
        fontSize = 16.sp,
        fontWeight = FontWeight.ExtraBold,
        lineHeight = 20.sp
    ),
    // Used for names like "Budi Setiawan" or "Oktober 2023"
    val titleCard: TextStyle = TextStyle(
        fontSize = 16.sp,
        fontWeight = FontWeight.Bold,
        lineHeight = 20.sp
    ),

    // --- BODY (Content & Details) ---
    // Used for important data like "No. Meter: 8820931102"
    val bodyProminent: TextStyle = TextStyle(
        fontSize = 14.sp,
        fontWeight = FontWeight.SemiBold,
        lineHeight = 20.sp
    ),
    // Used for addresses or general descriptions
    val bodyStandard: TextStyle = TextStyle(
        fontSize = 14.sp,
        fontWeight = FontWeight.Normal,
        lineHeight = 20.sp
    ),
    // Used for footer notes like "Biaya perawatan pipa"
    val bodySmall: TextStyle = TextStyle(
        fontSize = 12.sp,
        fontWeight = FontWeight.Normal,
        lineHeight = 16.sp
    ),

    // --- LABEL (Overlines & UI Controls) ---
    // Used for small grey caps like "CUSTOMER DETAILS"
    val labelOverline: TextStyle = TextStyle(
        fontSize = 10.sp,
        fontWeight = FontWeight.ExtraBold,
        lineHeight = 14.sp,
        letterSpacing = 1.15.sp // Increased tracking for uppercase styling
    ),
    // Used inside the "LUNAS" or "BELUM BAYAR" pills
    val labelBadge: TextStyle = TextStyle(
        fontSize = 10.sp,
        fontWeight = FontWeight.ExtraBold,
        lineHeight = 12.sp
    ),
    // Used inside Primary Buttons like "Bayar Sekarang" or "Login"
    val labelAction: TextStyle = TextStyle(
        fontSize = 16.sp,
        fontWeight = FontWeight.Bold,
        lineHeight = 20.sp
    ),
    // Used for currency prefix (Rp) or units (m³)
    val labelUnit: TextStyle = TextStyle(
        fontSize = 12.sp,
        fontWeight = FontWeight.Bold,
        lineHeight = 16.sp
    )
)

val LocalSimbanyuTypography = staticCompositionLocalOf { SimbanyuTypography() }