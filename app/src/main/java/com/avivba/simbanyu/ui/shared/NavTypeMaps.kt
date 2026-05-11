package com.avivba.simbanyu.ui.shared

import kotlin.reflect.typeOf

object NavTypeMaps {
    val Main = mapOf(
        typeOf<Screen.MainScreen>() to serializableNavType<Screen.MainScreen>()
    )
    
    val Invoice = mapOf(
        typeOf<List<String>>() to serializableNavType<List<String>>()
    )
}
