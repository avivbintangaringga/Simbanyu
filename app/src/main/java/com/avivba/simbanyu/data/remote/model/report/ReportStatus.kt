package com.avivba.simbanyu.data.remote.model.report

import androidx.annotation.Keep

@Keep
enum class ReportStatus {
    WAITING,
    IN_PROGRESS,
    COMPLETED,
    REJECTED
}
