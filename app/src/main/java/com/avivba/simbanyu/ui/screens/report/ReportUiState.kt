package com.avivba.simbanyu.ui.screens.report

import androidx.compose.foundation.text.input.TextFieldState
import com.avivba.simbanyu.ui.shared.model.Coordinate
import com.avivba.simbanyu.ui.shared.model.Report
import java.io.File

enum class Fields {
    LOCATION,
    DESCRIPTION,
    IMAGE,
}


data class ReportUiState(
    val isSendingReport: Boolean = false,
    val isRefreshing: Boolean = false,
    val isLoading: Boolean = false,
    val locationState: TextFieldState = TextFieldState(),
    val descriptionState: TextFieldState = TextFieldState(),
    val locationCoordinate: Coordinate? = null,
    val image: File? = null,
    val selectedTabIndex: Int = 0,
    val reports: List<Report> = emptyList(),
    val errorFields: List<Fields> = emptyList(),
)

sealed interface ReportAction {
    data object Load : ReportAction
    data object Refresh : ReportAction
    data object OpenCamera : ReportAction
    data class SetImage(val file: File) : ReportAction
    data class SetMyLocation(val coordinate: Coordinate) : ReportAction
    data object SendReport : ReportAction
    data class ChangeTab(val index: Int) : ReportAction
    data class ToggleExpandItem(val report: Report) : ReportAction
}

sealed interface ReportEvent {
    data object ReportSent : ReportEvent
    data object OpenCamera : ReportEvent
}