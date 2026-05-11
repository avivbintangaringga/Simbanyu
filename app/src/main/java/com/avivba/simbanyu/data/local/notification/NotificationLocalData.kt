package com.avivba.simbanyu.data.local.notification

import androidx.annotation.Keep
import kotlinx.serialization.Serializable

@Keep
@Serializable
data class NotificationLocalData(
    val readIds: Set<String> = emptySet()
)
