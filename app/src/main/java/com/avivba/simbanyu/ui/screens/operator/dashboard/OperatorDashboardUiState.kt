package com.avivba.simbanyu.ui.screens.operator.dashboard

data class OperatorDashboardUiState(
    val isLoading: Boolean = false,
    val name: String = "Operator"
)

sealed interface OperatorDashboardAction {
    data object LoadData : OperatorDashboardAction
}

sealed interface OperatorDashboardEvent {
    data class ShowError(val message: String) : OperatorDashboardEvent
}