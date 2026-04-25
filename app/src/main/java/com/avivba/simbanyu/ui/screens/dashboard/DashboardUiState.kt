package com.avivba.simbanyu.ui.screens.dashboard

import com.avivba.simbanyu.ui.shared.model.Announcement

data class WaterUsage(
    val usage: Int,
    val month: String,
    val isMaximum: Boolean
)

data class DashboardUiState(
    val isLoading: Boolean = false,
    val isRefreshing: Boolean = false,
    val name: String = "User",
    val bill: String = "",
    val waterUsage: List<WaterUsage> = emptyList(),
    val waterUsageAverage: Double = 0.0,
    val waterUsageMaximum: Int = 0,
    val waterUsageMaximumMonth: String = "-",
    val announcements: List<Announcement> = emptyList()
)

sealed interface DashboardAction {
    data object Load : DashboardAction
    data object Refresh : DashboardAction
    data object PayBill : DashboardAction
    data object ViewAllAnnouncements : DashboardAction
}

sealed interface DashboardEvent {
    data object ViewAllAnnouncements : DashboardEvent
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
    Announcement(
        id = "1",
        title = "Gangguan Distribusi",
        body = "Terjadi kebocoran pipa utama di Jl. Sudirman. Distribusi air akan terhenti sementara selama perbaikan.",
        time = "5 menit lalu"
    ),
    Announcement(
        id = "2",
        title = "Pemeliharaan Rutin",
        body = "Pembersihan reservoir pusat sedang berlangsung. Tekanan air mungkin akan menurun di wilayah Utara.",
        time = "1 hari lalu"
    ),
    Announcement(
        id = "3",
        title = "Layanan Normal",
        body = "Perbaikan di area Jl. Merdeka telah selesai. Pasokan air kini kembali normal di seluruh wilayah.",
        time = "3 hari lalu"
    )
)