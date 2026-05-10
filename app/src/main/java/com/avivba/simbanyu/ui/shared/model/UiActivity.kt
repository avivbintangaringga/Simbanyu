package com.avivba.simbanyu.ui.shared.model

import com.avivba.simbanyu.domain.model.Activity
import com.avivba.simbanyu.ui.utils.toRelativeTime
import java.time.Instant

data class UiActivity(
    val id: String,
    val title: String,
    val body: String,
    val date: String,
) {
    fun toDomain(): Activity {
        return Activity(
            id = this.id,
            title = this.title,
            body = this.body,
            date = Instant.parse(this.date)
        )
    }

    companion object {
        fun fromDomain(activity: Activity): com.avivba.simbanyu.ui.shared.model.UiActivity {
            return UiActivity(
                id = activity.id,
                title = activity.title,
                body = activity.body,
                date = activity.date.toRelativeTime()
            )
        }
    }
}