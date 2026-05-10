package com.avivba.simbanyu.ui.screens.dashboard

import com.avivba.simbanyu.ui.shared.model.UiAnnouncement

data class WaterUsage(
    val usage: Int,
    val month: String,
    val isMaximum: Boolean
)

data class DashboardUiState(
    val isLoading: Boolean = false,
    val isRefreshing: Boolean = false,
    val name: String = "User",
    val hasPendingBill: Boolean = false,
    val bill: String = "",
    val billIds: List<String> = emptyList(),
    val usage: List<WaterUsage> = emptyList(),
    val usageAverage: Double = 0.0,
    val usageMaximum: Int = 0,
    val usageMaximumMonth: String = "-",
    val announcements: List<UiAnnouncement> = emptyList()
)

sealed interface DashboardAction {
    data object Load : DashboardAction
    data object Refresh : DashboardAction
    data object PayBill : DashboardAction
    data object ViewAllAnnouncements : DashboardAction
}

sealed interface DashboardEvent {
    data object ViewAllAnnouncements : DashboardEvent
    data object GoToInvoice : DashboardEvent
    data object GoToBillHistory : DashboardEvent
}

val fakeWaterUsage = listOf(
    WaterUsage(usage = 12, month = "OKT", isMaximum = false),
    WaterUsage(usage = 15, month = "NOV", isMaximum = false),
    WaterUsage(usage = 10, month = "DES", isMaximum = false),
    WaterUsage(usage = 18, month = "JAN", isMaximum = false),
    WaterUsage(usage = 22, month = "FEB", isMaximum = true),
    WaterUsage(usage = 14, month = "MAR", isMaximum = false)
)

val fakeAnnouncements = listOf(
    UiAnnouncement(
        id = "1",
        title = "Gangguan Distribusi",
        body = "Terjadi kebocoran pipa utama di Jl. Sudirman. Distribusi air akan terhenti sementara selama perbaikan.",
        date = "5 menit lalu"
    ),
    UiAnnouncement(
        id = "2",
        title = "Pemeliharaan Rutin",
        body = "Pembersihan reservoir pusat sedang berlangsung. Tekanan air mungkin akan menurun di wilayah Utara.",
        date = "1 hari lalu"
    ),
    UiAnnouncement(
        id = "3",
        title = "Layanan Normal",
        body = "Perbaikan di area Jl. Merdeka telah selesai. Pasokan air kini kembali normal di seluruh wilayah.",
        date = "3 hari lalu"
    )
)