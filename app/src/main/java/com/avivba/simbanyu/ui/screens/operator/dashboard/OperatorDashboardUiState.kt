package com.avivba.simbanyu.ui.screens.operator.dashboard

data class OperatorDashboardUiState(
    val isLoading: Boolean = false
)

sealed interface OperatorDashboardAction {
    data object LoadData : OperatorDashboardAction
}

sealed interface OperatorDashboardEvent {
    data class ShowError(val message: String) : OperatorDashboardEvent
}