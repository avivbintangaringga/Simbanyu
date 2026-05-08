package com.avivba.simbanyu.ui.screens.report

import androidx.compose.foundation.text.input.TextFieldState
import com.avivba.simbanyu.ui.shared.model.Coordinate
import com.avivba.simbanyu.ui.shared.model.Report
import com.avivba.simbanyu.ui.shared.model.ReportStatus
import com.avivba.simbanyu.ui.shared.model.User
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
    data object GetMyLocation : ReportAction
    data object SendReport : ReportAction
    data class ChangeTab(val index: Int) : ReportAction
    data class ToggleExpandItem(val report: Report) : ReportAction
}

sealed interface ReportEvent {
    data object ReportSent : ReportEvent
    data object OpenCamera : ReportEvent
}

val fakeUser = User(
    id = "1",
    name = "Budi Budiman",
    meterId = "MTR1234432",
    role = "subscriber",
    imageUrl = ""
)

val fakeReports = listOf(
    Report(
        id = "REP-001",
        title = "Kebocoran Pipa Utama",
        user = fakeUser,
        status = ReportStatus.IN_PROGRESS,
        location = "Jl. Merdeka No. 10, Jakarta",
        locationCoordinate = Coordinate(-6.1754, 106.8272),
        description = "Pipa air depan rumah pecah, air meluap ke jalanan.",
        image = "https://example.com/leak1.jpg",
        date = "24 Mei 2024",
    ),
    Report(
        id = "REP-002",
        title = "Air Keruh dan Berpasir",
        user = fakeUser,
        status = ReportStatus.COMPLETED,
        location = "Perumahan Indah, Blok C2",
        locationCoordinate = Coordinate(-6.2000, 106.8166),
        description = "Sudah 2 hari air keluar berwarna coklat dan ada endapan pasir.",
        image = "https://example.com/muddy1.jpg",
        date = "20 Mei 2024"
    ),
    Report(
        id = "REP-003",
        title = "Aliran Air Mati Total",
        user = fakeUser,
        status = ReportStatus.WAITING,
        location = "Kp. Melayu RT 05/RW 02",
        locationCoordinate = Coordinate(-6.2333, 106.8500),
        description = "Air tidak mengalir sama sekali sejak tadi pagi pukul 06.00.",
        image = "https://example.com/outage1.jpg",
        date = "25 Mei 2024"
    ),
    Report(
        id = "REP-004",
        title = "Pipa Bocor Kecil",
        user = fakeUser,
        status = ReportStatus.REJECTED,
        location = "Jl. Sudirman Gg. 5",
        locationCoordinate = Coordinate(-6.2146, 106.8202),
        description = "Ada rembesan di sambungan meteran air.",
        image = "https://example.com/leak2.jpg",
        date = "15 April 2024"
    )
)
