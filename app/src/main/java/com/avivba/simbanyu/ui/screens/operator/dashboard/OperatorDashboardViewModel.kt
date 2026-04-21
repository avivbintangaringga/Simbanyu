package com.avivba.simbanyu.ui.screens.operator.dashboard

import androidx.lifecycle.viewModelScope
import com.avivba.simbanyu.ui.shared.BaseViewModel
import kotlinx.coroutines.launch

class OperatorDashboardViewModel(

) : BaseViewModel<OperatorDashboardUiState, OperatorDashboardAction, OperatorDashboardEvent>(
    OperatorDashboardUiState()
) {
    override fun onAction(action: OperatorDashboardAction) {
        viewModelScope.launch {
            when (action) {
                OperatorDashboardAction.LoadData -> load()
            }
        }
    }

    private fun load() {

    }
}