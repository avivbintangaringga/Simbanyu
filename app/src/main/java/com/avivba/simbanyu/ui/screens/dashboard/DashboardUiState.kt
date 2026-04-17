package com.avivba.simbanyu.ui.screens.dashboard

data class WaterUsage(
    val usage: Int,
    val month: String,
    val isMaximum: Boolean
)

data class Announcement(
    val title: String,
    val content: String,
    val time: String,
)

data class Bill(
    val amount: String,
    val period: String
)

data class DashboardUiState(
    val isLoading: Boolean = false,
    val isRefreshing: Boolean = false,
    val name: String = "User",
    val bill: Bill? = null,
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
        title = "Gangguan Distribusi",
        content = "Terjadi kebocoran pipa utama di Jl. Sudirman. Distribusi air akan terhenti sementara selama perbaikan.",
        time = "5 menit lalu"
    ),
    Announcement(
        title = "Pemeliharaan Rutin",
        content = "Pembersihan reservoir pusat sedang berlangsung. Tekanan air mungkin akan menurun di wilayah Utara.",
        time = "1 hari lalu"
    ),
    Announcement(
        title = "Layanan Normal",
        content = "Perbaikan di area Jl. Merdeka telah selesai. Pasokan air kini kembali normal di seluruh wilayah.",
        time = "3 hari lalu"
    )
)