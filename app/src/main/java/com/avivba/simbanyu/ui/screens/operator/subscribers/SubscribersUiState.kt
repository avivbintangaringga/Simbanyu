package com.avivba.simbanyu.ui.screens.operator.subscribers

import androidx.compose.foundation.text.input.TextFieldState
import com.avivba.simbanyu.ui.shared.model.UiCoordinate
import com.avivba.simbanyu.ui.shared.model.UiSubscriber

enum class FilterRecordState(
    val title: String
) {
    ALL("Semua"),
    NOT_RECORDED("Belum Dicatat"),
    RECORDED("Selesai"),
}

data class SubscribersUiState(
    val isLoading: Boolean = true,
    val isRefreshing: Boolean = false,
    val filteredSubscribers: List<UiSubscriber> = emptyList(),
    val filterRecordState: FilterRecordState = FilterRecordState.ALL,
    val searchBarState: TextFieldState = TextFieldState()
)

sealed interface SubscribersAction {
    data object Load : SubscribersAction
    data object Refresh : SubscribersAction
    data class ShowDetails(val subscriber: UiSubscriber) : SubscribersAction
    data class RecordMeter(val subscriber: UiSubscriber) : SubscribersAction
    data class SetRecordStateFilter(val filter: FilterRecordState) : SubscribersAction
}
sealed interface SubscribersEvent {
    data class GoToSubscriberDetails(val subscriber: UiSubscriber) : SubscribersEvent
    data class GoToRecordMeter(val subscriber: UiSubscriber) : SubscribersEvent
}

val fakeSubscribers = listOf(
    UiSubscriber(
        id = "SB001",
        name = "Budi Santoso",
        address = "Jl. Merdeka No. 10, Jakarta",
        phone = "081234567890",
        coordinate = UiCoordinate(-6.1751, 106.8272),
        plan = "Rumah Tangga A",
        meterId = "MTR001",
        lastMeterRecordDate = "2024-03-20 09:15",
        isRecorded = true,
        imageUrl = "https://picsum.photos/seed/sb1/200"
    ),
    UiSubscriber(
        id = "SB002",
        name = "Siti Aminah",
        address = "Gg. Mawar No. 5, Bandung",
        phone = "082198765432",
        coordinate = UiCoordinate(-6.9175, 107.6191),
        plan = "Rumah Tangga B",
        meterId = "MTR002",
        lastMeterRecordDate = "2024-02-15 14:30",
        isRecorded = false,
        imageUrl = "https://picsum.photos/seed/sb2/200"
    ),
    UiSubscriber(
        id = "SB003",
        name = "Agus Prayogo",
        address = "Perumahan Indah Blok C/12, Surabaya",
        phone = "085612345678",
        coordinate = UiCoordinate(-7.2575, 112.7521),
        plan = "Niaga",
        meterId = "MTR003",
        lastMeterRecordDate = "2024-03-18 10:00",
        isRecorded = true,
        imageUrl = "https://picsum.photos/seed/sb3/200"
    ),
    UiSubscriber(
        id = "SB004",
        name = "Dewi Lestari",
        address = "Jl. Sudirman No. 45, Yogyakarta",
        phone = "087811223344",
        coordinate = UiCoordinate(-7.7956, 110.3695),
        plan = "Rumah Tangga A",
        meterId = "MTR004",
        lastMeterRecordDate = "2024-02-28 16:45",
        isRecorded = false,
        imageUrl = "https://picsum.photos/seed/sb4/200"
    ),
    UiSubscriber(
        id = "SB005",
        name = "Eko Wijaya",
        address = "Jl. Diponegoro No. 8, Semarang",
        phone = "081344556677",
        coordinate = UiCoordinate(-6.9667, 110.4167),
        plan = "Sosial",
        meterId = "MTR005",
        lastMeterRecordDate = "2024-03-21 08:20",
        isRecorded = true,
        imageUrl = "https://picsum.photos/seed/sb5/200"
    ),
    UiSubscriber(
        id = "SB006",
        name = "Rina Malasari",
        address = "Komp. Hijau Asri E-9, Malang",
        phone = "081900998877",
        coordinate = UiCoordinate(-7.9839, 112.6214),
        plan = "Rumah Tangga B",
        meterId = "MTR006",
        lastMeterRecordDate = "2024-02-10 11:30",
        isRecorded = false,
        imageUrl = "https://picsum.photos/seed/sb6/200"
    ),
    UiSubscriber(
        id = "SB007",
        name = "Bambang Herlambang",
        address = "Jl. Gatot Subroto No. 21, Medan",
        phone = "085266778899",
        coordinate = UiCoordinate(3.5952, 98.6722),
        plan = "Industri",
        meterId = "MTR007",
        lastMeterRecordDate = "2024-03-19 13:00",
        isRecorded = true,
        imageUrl = "https://picsum.photos/seed/sb7/200"
    ),
    UiSubscriber(
        id = "SB008",
        name = "Sri Wahyuni",
        address = "Jl. Ahmad Yani No. 15, Makassar",
        phone = "082333444555",
        coordinate = UiCoordinate(-5.1476, 119.4327),
        plan = "Rumah Tangga A",
        meterId = "MTR008",
        lastMeterRecordDate = "2024-02-25 09:50",
        isRecorded = false,
        imageUrl = "https://picsum.photos/seed/sb8/200"
    ),
    UiSubscriber(
        id = "SB009",
        name = "Hendra Setiawan",
        address = "Gg. Damai No. 3, Palembang",
        phone = "08112223334",
        coordinate = UiCoordinate(-2.9761, 104.7754),
        plan = "Rumah Tangga B",
        meterId = "MTR009",
        lastMeterRecordDate = "2024-03-15 15:10",
        isRecorded = true,
        imageUrl = "https://picsum.photos/seed/sb9/200"
    ),
    UiSubscriber(
        id = "SB010",
        name = "Ani Suryani",
        address = "Jl. Veteran No. 50, Denpasar",
        phone = "081766554433",
        coordinate = UiCoordinate(-8.6705, 115.2126),
        plan = "Niaga",
        meterId = "MTR010",
        lastMeterRecordDate = "2024-02-20 17:25",
        isRecorded = false,
        imageUrl = "https://picsum.photos/seed/sb10/200"
    )
)

