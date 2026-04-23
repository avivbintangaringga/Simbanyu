package com.avivba.simbanyu.ui.screens.operator.subsribers

import androidx.compose.foundation.text.input.TextFieldState
import com.avivba.simbanyu.ui.shared.model.Coordinate
import com.avivba.simbanyu.ui.shared.model.Subscriber

enum class FilterRecordState(
    val title: String
) {
    ALL("Semua"),
    NOT_RECORDED("Belum Dicatat"),
    RECORDED("Selesai"),
}

data class SubscribersUiState(
    val isLoading: Boolean = false,
    val isRefreshing: Boolean = false,
    val filteredSubscribers: List<Subscriber> = emptyList(),
    val filterRecordState: FilterRecordState = FilterRecordState.ALL,
    val searchBarState: TextFieldState = TextFieldState()
)

sealed interface SubscribersAction {
    data object Load : SubscribersAction
    data object Refresh : SubscribersAction
    data class ShowDetails(val subscriber: Subscriber) : SubscribersAction
    data class RecordMeter(val subscriber: Subscriber) : SubscribersAction
    data class SetRecordStateFilter(val filter: FilterRecordState) : SubscribersAction
}
sealed interface SubscribersEvent {
    data class GoToSubscriberDetails(val subscriber: Subscriber) : SubscribersEvent
    data class GoToRecordMeter(val subscriber: Subscriber) : SubscribersEvent
}

val fakeSubscribers = listOf(
    Subscriber(
        id = "SB001",
        name = "Budi Santoso",
        address = "Jl. Merdeka No. 10, Jakarta",
        phone = "081234567890",
        coordinate = Coordinate(-6.1751, 106.8272),
        category = "Rumah Tangga A",
        meterId = "MTR001",
        lastMeterRecordDate = "2024-03-20 09:15",
        isRecorded = true,
        imageUrl = "https://picsum.photos/seed/sb1/200"
    ),
    Subscriber(
        id = "SB002",
        name = "Siti Aminah",
        address = "Gg. Mawar No. 5, Bandung",
        phone = "082198765432",
        coordinate = Coordinate(-6.9175, 107.6191),
        category = "Rumah Tangga B",
        meterId = "MTR002",
        lastMeterRecordDate = "2024-02-15 14:30",
        isRecorded = false,
        imageUrl = "https://picsum.photos/seed/sb2/200"
    ),
    Subscriber(
        id = "SB003",
        name = "Agus Prayogo",
        address = "Perumahan Indah Blok C/12, Surabaya",
        phone = "085612345678",
        coordinate = Coordinate(-7.2575, 112.7521),
        category = "Niaga",
        meterId = "MTR003",
        lastMeterRecordDate = "2024-03-18 10:00",
        isRecorded = true,
        imageUrl = "https://picsum.photos/seed/sb3/200"
    ),
    Subscriber(
        id = "SB004",
        name = "Dewi Lestari",
        address = "Jl. Sudirman No. 45, Yogyakarta",
        phone = "087811223344",
        coordinate = Coordinate(-7.7956, 110.3695),
        category = "Rumah Tangga A",
        meterId = "MTR004",
        lastMeterRecordDate = "2024-02-28 16:45",
        isRecorded = false,
        imageUrl = "https://picsum.photos/seed/sb4/200"
    ),
    Subscriber(
        id = "SB005",
        name = "Eko Wijaya",
        address = "Jl. Diponegoro No. 8, Semarang",
        phone = "081344556677",
        coordinate = Coordinate(-6.9667, 110.4167),
        category = "Sosial",
        meterId = "MTR005",
        lastMeterRecordDate = "2024-03-21 08:20",
        isRecorded = true,
        imageUrl = "https://picsum.photos/seed/sb5/200"
    ),
    Subscriber(
        id = "SB006",
        name = "Rina Malasari",
        address = "Komp. Hijau Asri E-9, Malang",
        phone = "081900998877",
        coordinate = Coordinate(-7.9839, 112.6214),
        category = "Rumah Tangga B",
        meterId = "MTR006",
        lastMeterRecordDate = "2024-02-10 11:30",
        isRecorded = false,
        imageUrl = "https://picsum.photos/seed/sb6/200"
    ),
    Subscriber(
        id = "SB007",
        name = "Bambang Herlambang",
        address = "Jl. Gatot Subroto No. 21, Medan",
        phone = "085266778899",
        coordinate = Coordinate(3.5952, 98.6722),
        category = "Industri",
        meterId = "MTR007",
        lastMeterRecordDate = "2024-03-19 13:00",
        isRecorded = true,
        imageUrl = "https://picsum.photos/seed/sb7/200"
    ),
    Subscriber(
        id = "SB008",
        name = "Sri Wahyuni",
        address = "Jl. Ahmad Yani No. 15, Makassar",
        phone = "082333444555",
        coordinate = Coordinate(-5.1476, 119.4327),
        category = "Rumah Tangga A",
        meterId = "MTR008",
        lastMeterRecordDate = "2024-02-25 09:50",
        isRecorded = false,
        imageUrl = "https://picsum.photos/seed/sb8/200"
    ),
    Subscriber(
        id = "SB009",
        name = "Hendra Setiawan",
        address = "Gg. Damai No. 3, Palembang",
        phone = "08112223334",
        coordinate = Coordinate(-2.9761, 104.7754),
        category = "Rumah Tangga B",
        meterId = "MTR009",
        lastMeterRecordDate = "2024-03-15 15:10",
        isRecorded = true,
        imageUrl = "https://picsum.photos/seed/sb9/200"
    ),
    Subscriber(
        id = "SB010",
        name = "Ani Suryani",
        address = "Jl. Veteran No. 50, Denpasar",
        phone = "081766554433",
        coordinate = Coordinate(-8.6705, 115.2126),
        category = "Niaga",
        meterId = "MTR010",
        lastMeterRecordDate = "2024-02-20 17:25",
        isRecorded = false,
        imageUrl = "https://picsum.photos/seed/sb10/200"
    )
)

