package com.avivba.simbanyu.ui.utils

import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.util.Locale

fun Double.toDottedString(): String {
    val symbols = DecimalFormatSymbols(Locale.getDefault()).apply {
        groupingSeparator = '.'
    }
    val formatter = DecimalFormat("#,###", symbols)
    return formatter.format(this)
}

fun Int.toDottedString(): String {
    return this.toDouble().toDottedString()
}
