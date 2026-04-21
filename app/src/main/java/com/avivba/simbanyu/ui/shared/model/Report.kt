package com.avivba.simbanyu.ui.shared.model

import com.avivba.simbanyu.ui.screens.report.ReportType

data class Report(
    val id: String,
    val title: String,
    val type: ReportType,
    val status: String,
    val location: String,
    val locationCoordinate: Coordinate,
    val description: String,
    val image: String,
    val date: String,
    val isExpanded: Boolean = false,
)
