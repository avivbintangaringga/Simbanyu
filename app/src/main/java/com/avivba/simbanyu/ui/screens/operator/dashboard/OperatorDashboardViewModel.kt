package com.avivba.simbanyu.ui.screens.operator.dashboard

import androidx.lifecycle.viewModelScope
import com.avivba.simbanyu.ui.shared.base.BaseViewModel
import kotlinx.coroutines.launch

class OperatorDashboardViewModel(

) : BaseViewModel<OperatorDashboardUiState, OperatorDashboardAction, OperatorDashboardEvent>(
    OperatorDashboardUiState()
) {
    init {
        load()
    }

    override fun onAction(action: OperatorDashboardAction) {
        viewModelScope.launch {
            when (action) {
                OperatorDashboardAction.LoadData -> load()
            }
        }
    }

    private fun load() {
        viewModelScope.launch {
            updateState {
                fakeOperatorDashboardUiState
            }
        }
    }
}