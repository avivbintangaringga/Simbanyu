package com.avivba.simbanyu.ui.utils

import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.util.Locale

fun Double.toDottedCurrency(prefix: String = "Rp "): String {
    val symbols = DecimalFormatSymbols(Locale.getDefault()).apply {
        groupingSeparator = '.'
    }
    val formatter = DecimalFormat("#,###", symbols)
    return prefix + formatter.format(this)
}

fun Int.toDottedCurrency(prefix: String = "Rp "): String {
    return this.toDouble().toDottedCurrency(prefix)
}
