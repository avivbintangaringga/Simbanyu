package com.avivba.simbanyu.ui.screens.operator.dashboard

import com.avivba.simbanyu.ui.shared.Screen
import com.avivba.simbanyu.ui.shared.model.ActivityData

data class OperatorDashboardUiState(
    val isLoading: Boolean = false,
    val isRefreshing: Boolean = false,
    val name: String = "Operator",
    val activities: List<ActivityData> = emptyList()
)

sealed interface OperatorDashboardAction {
    data object Load : OperatorDashboardAction
    data object Refresh : OperatorDashboardAction
    data object ViewAllAnnouncements: OperatorDashboardAction
}

sealed interface OperatorDashboardEvent {
    data object GoToAnnouncementsPage : OperatorDashboardEvent
}

val fakeOperatorDashboardUiState = OperatorDashboardUiState(
    isLoading = false,
    name = "Budi Santoso",
    activities = listOf(
        ActivityData(
            id = "1",
            title = "Pencatatan Meter",
            body = "Berhasil mencatat meteran untuk Pelanggan #1024",
            date = "10:30 AM"
        ),
        ActivityData(
            id = "2",
            title = "Pengaduan Selesai",
            body = "Perbaikan pipa bocor di Jl. Merdeka telah selesai",
            date = "09:15 AM"
        ),
        ActivityData(
            id = "3",
            title = "Pemasangan Baru",
            body = "Instalasi meteran baru untuk Toko Berkah",
            date = "Yesterday"
        )
    )
)

