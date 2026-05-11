package com.avivba.simbanyu.ui.shared

import android.os.Bundle
import androidx.navigation.NavType
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

inline fun <reified T> serializableNavType(
    isNullableAllowed: Boolean = false
) = object : NavType<T>(isNullableAllowed) {
    override fun get(bundle: Bundle, key: String): T? {
        return bundle.getString(key)?.let { Json.decodeFromString(it) }
    }

    override fun parseValue(value: String): T {
        return Json.decodeFromString(value)
    }

    override fun put(bundle: Bundle, key: String, value: T) {
        bundle.putString(key, Json.encodeToString(value))
    }

    override fun serializeAsValue(value: T): String {
        return Json.encodeToString(value)
    }
}
