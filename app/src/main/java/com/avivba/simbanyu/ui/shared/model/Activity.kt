package com.avivba.simbanyu.ui.shared.model

import com.avivba.simbanyu.domain.model.Activity
import java.time.Instant

data class Activity(
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
}

fun Activity.toUi(): com.avivba.simbanyu.ui.shared.model.Activity {
    return Activity(
        id = this.id,
        title = this.title,
        body = this.body,
        date = this.date.toString()
    )
}